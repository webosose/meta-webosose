# Copyright (c) 2017 LG Electronics, Inc.

EXTENDPRAUTO_append = "webosrpi2"

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

SRC_URI_append = " file://wired-setup"

do_install_append() {
    #Configure wired network interface for raspberrypi
    install -d ${D}${datadir}/connman
    install -m 0755 ${WORKDIR}/wired-setup ${D}${datadir}/connman
}
