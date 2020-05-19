# Copyright (c) 2019-2020 LG Electronics, Inc.

EXTENDPRAUTO_append = "webosrpi2"

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"
SRC_URI += "file://0001-hciuart-lower-the-speed-and-restart-on-failure.patch"

do_install_append() {
    rm -vf ${D}${systemd_system_unitdir}/bthelper@.service
    rm -vf ${D}${bindir}/bthelper
    rm -vrf ${D}${sysconfdir}
}
SYSTEMD_SERVICE_${PN}_remove = "bthelper@.service"
