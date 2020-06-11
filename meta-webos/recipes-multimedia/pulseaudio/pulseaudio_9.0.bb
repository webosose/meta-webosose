# Copyright (c) 2014-2020 LG Electronics, Inc.

require recipes-multimedia/pulseaudio/pulseaudio.inc

# Restore the LIC_FILES_CHKSUM for 9.0 version
# pulseaudio.inc is already using different one for 10.0
LICENSE = "GPLv2+ & LGPLv2.1"
LIC_FILES_CHKSUM = "file://LICENSE;md5=d9ae089c8dc5339f8ac9d8563038a29f \
                    file://GPL;md5=4325afd396febcb659c36b49533135d4 \
                    file://LGPL;md5=2d5025d4aa3495befef8f17206a5b0a1 \
                    file://src/pulsecore/resampler.h;beginline=4;endline=21;md5=09794012ae16912c0270f3280cc8ff84 \
                    file://oss-pkg-info.yaml;md5=5c4ec8703e42f87fdf96fa3794704163 \
"

# removed from oe-core's pulseaudio.inc in upgrade from 9.0 to 10.0
# commit 4ddaf28fd36294fd940f26d55973da20eeeeb0d8
# Author:  Tanu Kaskinen <tanuk@iki.fi>
# Date:    Fri Feb 3 09:06:35 2017 +0200
# Subject: pulseaudio: 9.0 -> 10.0
DEPENDS += "json-c gdbm"
# removed from oe-core's pulseaudio.inc in upgrade from 12.2 to 13.0
# commit e4b9e98100cdeb74d4898afcab2d76f2e0855960
# Author: Tanu Kaskinen <tanuk@iki.fi>
# Date:   Thu Nov 21 16:06:24 2019 +0200
# Subject: pulseaudio: 12.2 -> 13.0
#
# Release notes:
# https://www.freedesktop.org/wiki/Software/PulseAudio/Notes/13.0/
#
# Dropped intltool-native from DEPENDS. The .desktop file translations
# don't need intltool any more, gettext is enough.
DEPENDS += "intltool-native"

# This is blacklisted because of the license
DEPENDS_remove = "libatomic-ops"

DEPENDS += "pmloglib"

WEBOS_VERSION = "9.0-19_ccc5db5aea9119cb4aaf177aebe87e2584a2aed7"
PR = "r22"

inherit webos_enhanced_submissions

inherit webos_public_repo

WEBOS_REPO_NAME = "pulseaudio-webos"
SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE} \
          file://0001-change-alsa_ucm-header-path.patch \
          file://pulseaudio.service \
"

S = "${WORKDIR}/git"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
EXTRA_OECONF += "--with-access-group=root \
                --disable-samplerate \
                "

# Added to oe-core pulseaudio.inc when upgrading to 12.2 version
# but our old 9.* version doesn't support it
EXTRA_OECONF_remove = "--disable-gsettings"

# Added to oe-core pulseaudio.inc to improve build reproducibility with 12.2 version
# but our old 9.* version doesn't support it
EXTRA_OECONF_remove = "--disable-running-from-build-tree"

PACKAGECONFIG ??= "${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'systemd', '', d)} \
                   ${@bb.utils.contains('DISTRO_FEATURES', 'zeroconf', 'avahi', '', d)} \
                   ${@bb.utils.contains('DISTRO_FEATURES', 'x11', 'x11', '', d)} \
                   palm-resampler \
                   dbus \
"

PACKAGECONFIG[palm-resampler] = "--enable-palm-resampler,--disable-palm-resampler"

do_install_prepend() {
   install -v -m 0644 ${S}/volatiles.04_pulse ${WORKDIR}/volatiles.04_pulse
}

do_install_append() {
   install -v -d ${D}${sysconfdir}/systemd/system
   install -v -m 644 ${WORKDIR}/pulseaudio.service ${D}${sysconfdir}/systemd/system/
   install -v -m 644 ${S}/src/modules/module-palm-policy-default.h ${D}${includedir}/pulse/module-palm-policy.h
   install -v -m 644 ${S}/src/modules/module-palm-policy-tables-default.h ${D}${includedir}/pulse/module-palm-policy-tables.h
}
do_install_append_webos() {
   install -v -m 644 ${S}/palm/open_system.pa ${D}${sysconfdir}/pulse/system.pa
}
do_install_append_qemuall() {
    install -v -m 644 ${S}/palm/qemux86_system.pa ${D}${sysconfdir}/pulse/system.pa
}

FILES_${PN}-dev += "${libdir}/pulse-9.0/modules/*.la ${datadir}/vala ${libdir}/cmake"

RDEPENDS_pulseaudio-server_append = "\
    pulseaudio-module-palm-policy \
    pulseaudio-module-null-source \
    pulseaudio-module-rtp-send \
    pulseaudio-module-rtp-recv \
"

python populate_packages_prepend() {

    plugindir = d.expand('${libdir}/pulse-9.0/modules/')
    do_split_packages(d, plugindir, '^module-(.*)\.so$', 'pulseaudio-module-%s', 'PulseAudio module for %s', extra_depends='', prepend=True)
    do_split_packages(d, plugindir, '^lib(.*)\.so$', 'pulseaudio-lib-%s', 'PulseAudio library for %s', extra_depends='', prepend=True)
}

# ERROR: pulseaudio-9.0-13-r20 do_package_qa: QA Issue: pulseaudio-pa-info rdepends on bash, but it isn't a build dependency, missing bash in DEPENDS or PACKAGECONFIG? [build-deps]
VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS_${PN}-pa-info_append_class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS_${PN}-pa-info_remove_class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"
