# Copyright (c) 2022 LG Electronics, Inc.

require pulseaudio_15.0.inc

LICENSE = "GPL-2.0-or-later & LGPL-2.1-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=0e5cd938de1a7a53ea5adac38cc10c39 \
                    file://GPL;md5=4325afd396febcb659c36b49533135d4 \
                    file://LGPL;md5=2d5025d4aa3495befef8f17206a5b0a1 \
                    file://src/pulsecore/resampler.h;beginline=4;endline=21;md5=09794012ae16912c0270f3280cc8ff84 \
                    file://oss-pkg-info.yaml;md5=5c4ec8703e42f87fdf96fa3794704163 \
"

# This is blacklisted because of the license
DEPENDS:remove = "libatomic-ops"

DEPENDS += "pmloglib"

WEBOS_VERSION = "15.0-34_6e214e382862fb6288b3f7ae59f25893cb0c2810"
PR = "r0"

inherit webos_enhanced_submissions

inherit webos_public_repo

WEBOS_REPO_NAME = "pulseaudio-webos"
SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE} \
    file://pulseaudio.service \
    file://0001-client-conf-Add-allow-autospawn-for-root.patch \
    file://0002-do-not-display-CLFAGS-to-improve-reproducibility-bui.patch \
    file://0001-meson-Check-for-__get_cpuid.patch \
    file://0001-doxygen-meson.build-remove-dependency-on-doxygen-bin.patch \
"

S = "${WORKDIR}/git"

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"
EXTRA_OECONF += "--with-access-group=root \
                --disable-samplerate \
                "

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
}
do_install:append:webos() {
    install -v -m 644 ${S}/palm/open_system.pa ${D}${sysconfdir}/pulse/system.pa
}
do_install:append:qemuall() {
    install -v -m 644 ${S}/palm/qemux86_system.pa ${D}${sysconfdir}/pulse/system.pa
}

FILES:${PN}-dev += "${libdir}/pulse-15.0/modules/*.la ${datadir}/vala ${libdir}/cmake"

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

python populate_packages:prepend() {
    plugindir = d.expand('${libdir}/pulse-15.0/modules/')
    do_split_packages(d, plugindir, r'^module-(.*)\.so$', 'pulseaudio-module-%s', 'PulseAudio module for %s', extra_depends='', prepend=True)
    do_split_packages(d, plugindir, r'^lib(.*)\.so$', 'pulseaudio-lib-%s', 'PulseAudio library for %s', extra_depends='', prepend=True)
}

# QA Issue: pulseaudio-pa-info rdepends on bash, but it isn't a build dependency, missing bash in DEPENDS or PACKAGECONFIG? [build-deps]
VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS:${PN}-pa-info:append:class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS:${PN}-pa-info:remove:class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"

