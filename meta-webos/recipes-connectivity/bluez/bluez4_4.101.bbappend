# Copyright (c) 2014 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos1"

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

RDEPENDS_${PN} += "bluez4-conf"

SRC_URI += "file://bluetooth.conf.upstart"

do_install_append() {
    install -d ${D}${sysconfdir}/init
    install -m 0644 ${WORKDIR}/bluetooth.conf.upstart ${D}${sysconfdir}/init/bluetooth.conf
}

FILES_${PN} += "${sysconfdir}/init/bluetooth.conf"
