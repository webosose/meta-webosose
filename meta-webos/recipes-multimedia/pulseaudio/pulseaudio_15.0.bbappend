# Copyright (c) 2022-2025 LG Electronics, Inc.

AUTHOR = "Sushovan G <sushovan.g@lge.com>"

LIC_FILES_CHKSUM += "${LIC_FILES_CHKSUM_OSS_PKG_INFO}"
# License checksum for pulseaudio and kissfft
LIC_FILES_CHKSUM_OSS_PKG_INFO = "file://oss-pkg-info.yaml;md5=aad9c121c4d20efb9738300768a550c5"

# This is blacklisted because of the license
DEPENDS:remove = "libatomic-ops"

DEPENDS += "pmloglib tensorflow-lite flatbuffers webrtc-audio-processing-1 libpbnjson"

WEBOS_VERSION = "15.0-59_20a7d627df8ba33337749faf0891cda380854aa8"
EXTENDPRAUTO:append = "webos17"

inherit webos_enhanced_submissions

inherit webos_public_repo

WEBOS_REPO_NAME = "pulseaudio-webos"
FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"
SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE} \
    file://0001-meson.build-use-C-17.patch \
"

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
