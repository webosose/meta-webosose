# Copyright (c) 2022-2024 LG Electronics, Inc.

AUTHOR = "Sushovan G <sushovan.g@lge.com>"

LIC_FILES_CHKSUM += "${LIC_FILES_CHKSUM_OSS_PKG_INFO}"
# License checksum for pulseaudio and kissfft
LIC_FILES_CHKSUM_OSS_PKG_INFO = "file://oss-pkg-info.yaml;md5=aad9c121c4d20efb9738300768a550c5"

# This is blacklisted because of the license
DEPENDS:remove = "libatomic-ops"

DEPENDS += "pmloglib tensorflow-lite flatbuffers webrtc-audio-processing libpbnjson"

WEBOS_VERSION = "15.0-58_a79e2208efd0afd061038a688d06df2ba9bf3e86"
EXTENDPRAUTO:append = "webos14"

inherit webos_enhanced_submissions

inherit webos_public_repo

WEBOS_REPO_NAME = "pulseaudio-webos"
FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"
SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"

S = "${WORKDIR}/git"

inherit webos_systemd
WEBOS_SYSTEMD_SERVICE = "pulseaudio.service"

EXTRA_OECONF += " \
    --with-access-group=root \
    --disable-samplerate \
"

PACKAGECONFIG[palm-resampler] = "-Dpalm-resampler=true,-Dpalm-resampler=false,"
PACKAGECONFIG[rpi] = "-Drpi=true,-Drpi=false,"

# Compared to oe-core default, remove gsettings, add palm-resampler and add ofono even without 3g in DISTRO_FEATURES
PACKAGECONFIG ??= "${@bb.utils.contains('DISTRO_FEATURES', 'bluetooth', 'bluez5', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'zeroconf', 'avahi', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', '3g', 'ofono', '', d)} \
    ${@bb.utils.filter('DISTRO_FEATURES', 'ipv6 systemd x11', d)} \
    dbus \
    palm-resampler \
    ofono \
"

PACKAGECONFIG:append:rpi = " rpi"

do_install:prepend() {
    install -v -m 0644 ${S}/volatiles.04_pulse ${WORKDIR}/volatiles.04_pulse
}

do_install:append() {
    install -v -m 644 ${S}/src/modules/module-palm-policy-default.h ${D}${includedir}/pulse/module-palm-policy.h
    install -v -m 644 ${S}/src/modules/module-palm-policy-tables-default.h ${D}${includedir}/pulse/module-palm-policy-tables.h
}
do_install:append:webos() {
    install -v -m 644 ${S}/palm/open_system.pa ${D}${sysconfdir}/pulse/system.pa
    install -v -d ${D}${libdir}/pulse-15.0/modules/audioeffects/preprocess
    install -v -m 644 ${S}/src/modules/preprocess-source/hann.txt ${D}${libdir}/pulse-15.0/modules/audioeffects/preprocess/hann.txt
    install -v -m 644 ${S}/src/modules/preprocess-source/model_ecnr.tflite ${D}${libdir}/pulse-15.0/modules/audioeffects/preprocess/model_ecnr.tflite
    install -v -m 644 ${S}/src/modules/drc/sndfilter.txt ${D}${sysconfdir}/pulse/sndfilter.txt
    install -v -m 644 ${S}/src/modules/preprocess-source/config.txt ${D}${sysconfdir}/pulse/preprocessingAudioEffect.json
}
do_install:append:qemuall() {
    install -v -m 644 ${S}/palm/qemux86_system.pa ${D}${sysconfdir}/pulse/system.pa
}

FILES:${PN} += "${libdir}/pulse-15.0/modules/audioeffects/preprocess/*"


RDEPENDS:pulseaudio-server:append:webos = "\
    pulseaudio-module-postprocess-sink \
    pulseaudio-module-preprocess-source \
"

RDEPENDS:pulseaudio-server:append = "\
    pulseaudio-module-palm-policy \
    pulseaudio-module-null-source \
    pulseaudio-module-rtp-send \
    pulseaudio-module-rtp-recv \
    pulseaudio-module-loopback \
    pulseaudio-module-combine-sink \
    pulseaudio-module-ladspa-sink \
    ${@bb.utils.contains('PACKAGECONFIG', 'bluez5', '\
        pulseaudio-lib-bluez5-util \
        pulseaudio-module-bluetooth-discover \
        pulseaudio-module-bluetooth-policy \
        pulseaudio-module-bluez5-device \
        pulseaudio-module-bluez5-discover \
    ', '', d)} \
"

# This is needed because pulseaudio.inc uses
# "${libdir}/pulse-${PV}/modules/"
# and our PV "15.0-34" doesn't match it, this can be dropped after upgrading to pulseaudio-16 which uses just ${libdir}/pulseaudio/modules/
# https://git.openembedded.org/openembedded-core/commit/?id=e5399a09bf44700f97607b283379172dac0cf9c7
python populate_packages:prepend() {
    plugindir = d.expand('${libdir}/pulse-15.0/modules/')
    do_split_packages(d, plugindir, r'^module-(.*)\.so$', 'pulseaudio-module-%s', 'PulseAudio module for %s', extra_depends='', prepend=True)
    do_split_packages(d, plugindir, r'^lib(.*)\.so$', 'pulseaudio-lib-%s', 'PulseAudio library for %s', extra_depends='', prepend=True)
}

# QA Issue: pulseaudio-pa-info rdepends on bash, but it isn't a build dependency, missing bash in DEPENDS or PACKAGECONFIG? [build-deps]
VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS:${PN}-pa-info:append:class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS:${PN}-pa-info:remove:class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"

# http://gecko.lge.com:8000/Errors/Details/822524
# src/pulsecore/resampler.c:1744:26: error: assignment to 'unsigned int (*)(pa_resampler *, const pa_memchunk *, unsigned int,  pa_memchunk *, unsigned int *)' from incompatible pointer type 'void (*)(pa_resampler *, const pa_memchunk *, unsigned int,  pa_memchunk *, unsigned int *)' [-Wincompatible-pointer-types]
# src/pulsecore/resampler.c:1824:23: error: passing argument 1 of 'palm_free' from incompatible pointer type [-Wincompatible-pointer-types]
# src/pulsecore/resampler.c:1846:19: error: passing argument 1 of 'palm_free' from incompatible pointer type [-Wincompatible-pointer-types]
# src/pulsecore/resampler.c:2154:27: error: implicit declaration of function 'fabsf' [-Wimplicit-function-declaration]
# src/pulsecore/resampler.c:2289:34: error: implicit declaration of function 'av_resample'; did you mean 'palm_resample'? [-Wimplicit-function-declaration]
# src/pulsecore/resampler.c:2326:9: error: implicit declaration of function 'av_resample_close' [-Wimplicit-function-declaration]
# src/pulsecore/resampler.c:2343:30: error: assignment to 'struct AVResampleContext *' from 'int' makes pointer from integer without a cast [-Wint-conversion]
# src/pulsecore/resampler.c:2343:32: error: implicit declaration of function 'av_resample_init'; did you mean 'speex_resample_int'? [-Wimplicit-function-declaration]
# src/pulse/simple.c:390:41: error: passing argument 3 of 'pa_stream_cork' from incompatible pointer type [-Wincompatible-pointer-types]
CFLAGS += "-Wno-error=incompatible-pointer-types -Wno-error=int-conversion -Wno-error=implicit-function-declaration"
