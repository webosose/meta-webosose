# Copyright (c) 2014-2017 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos5"

RDEPENDS_${PN} += "bluez5-conf"
FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

SRC_URI += " \
    file://bluez.conf.upstart \
    file://obexd.conf.upstart \
"

do_install_append() {
    install -d ${D}${sysconfdir}/init
    install -m 0644 ${WORKDIR}/bluez.conf.upstart ${D}${sysconfdir}/init/bluez.conf
    install -m 0644 ${WORKDIR}/obexd.conf.upstart ${D}${sysconfdir}/init/obexd.conf
}

FILES_${PN} += "${sysconfdir}/init/bluez.conf"
FILES_${PN}-obex += "${sysconfdir}/init/obexd.conf"
