# Copyright (c) 2014-2018 LG Electronics, Inc.

require recipes-multimedia/pulseaudio/pulseaudio.inc

# This is blacklisted because of the license
DEPENDS_remove = "libatomic-ops"

DEPENDS += "pmloglib"

WEBOS_VERSION = "9.0-4_77c1479e857c03f5fee9f87602e26f208000c6a8"
PR = "r19"

inherit webos_enhanced_submissions

inherit webos_public_repo

WEBOS_REPO_NAME = "pulseaudio-webos"
SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

EXTRA_OECONF += "--with-access-group=root \
                --disable-samplerate \
                "


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

