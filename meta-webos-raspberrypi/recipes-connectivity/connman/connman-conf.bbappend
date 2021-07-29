# Copyright (c) 2017 LG Electronics, Inc.

EXTENDPRAUTO:append = "webosrpi2"

FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

SRC_URI:append = " file://wired-setup"

do_install:append() {
    #Configure wired network interface for raspberrypi
    install -d ${D}${datadir}/connman
    install -m 0755 ${WORKDIR}/wired-setup ${D}${datadir}/connman
}
