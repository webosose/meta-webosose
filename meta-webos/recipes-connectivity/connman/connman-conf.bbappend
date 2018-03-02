# Copyright (c) 2017 LG Electronics, Inc.

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

EXTENDPRAUTO_append = "webos1"

SRC_URI += " \
    file://main.conf \
"

do_install_append() {
    install -d ${D}${sysconfdir}/connman
    install -m 0644 ${WORKDIR}/main.conf ${D}${sysconfdir}/connman/
}

FILES_${PN} += "${sysconfdir}/connman"
