# Copyright (c) 2022 LG Electronics, Inc.

AUTHOR = "Sushovan G <sushovan.g@lge.com>"

LIC_FILES_CHKSUM += "file://oss-pkg-info.yaml;md5=5c4ec8703e42f87fdf96fa3794704163"

# This is blacklisted because of the license
DEPENDS:remove = "libatomic-ops"

DEPENDS += "pmloglib tensorflow-lite flatbuffers"

WEBOS_VERSION = "15.0-41_913611e76ffff60afc84bfd6165d3ea94a9175c1"
EXTENDPRAUTO:append = "webos3"

inherit webos_enhanced_submissions

inherit webos_public_repo

WEBOS_REPO_NAME = "pulseaudio-webos"
FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"
SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE} \
    file://pulseaudio.service \
    file://0001-client-conf-Add-allow-autospawn-for-root.patch \
    file://0002-do-not-display-CLFAGS-to-improve-reproducibility-bui.patch \
    file://0001-meson-Check-for-__get_cpuid.patch \
    file://0001-doxygen-meson.build-remove-dependency-on-doxygen-bin.patch \
    file://0001-meson.build-use-cpp_std-c-14.patch \
"

S = "${WORKDIR}/git"

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
    install -v -d ${D}${sysconfdir}/systemd/system
    install -v -m 644 ${WORKDIR}/pulseaudio.service ${D}${sysconfdir}/systemd/system/
    install -v -m 644 ${S}/src/modules/module-palm-policy-default.h ${D}${includedir}/pulse/module-palm-policy.h
    install -v -m 644 ${S}/src/modules/module-palm-policy-tables-default.h ${D}${includedir}/pulse/module-palm-policy-tables.h

    install -v -d ${D}${libdir}/pulse-15.0/modules/ecnr
}
do_install:append:webos() {
    install -v -m 644 ${S}/palm/open_system.pa ${D}${sysconfdir}/pulse/system.pa
    install -v -m 644 ${S}/src/modules/ecnr/hann.txt ${D}${libdir}/pulse-15.0/modules/ecnr/hann.txt
    install -v -m 644 ${S}/src/modules/ecnr/model_ecnr.tflite ${D}${libdir}/pulse-15.0/modules/ecnr/model_ecnr.tflite
}
do_install:append:qemuall() {
    install -v -m 644 ${S}/palm/qemux86_system.pa ${D}${sysconfdir}/pulse/system.pa
}
FILES:${PN} += "${libdir}/pulse-15.0/modules/ecnr"
FILES:${PN} += "${libdir}/pulse-15.0/modules/ecnr/*"

RDEPENDS:pulseaudio-server:append:webos = "\
    pulseaudio-module-ecnr \
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

