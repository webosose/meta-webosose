# Copyright (c) 2019-2025 LG Electronics, Inc.

EXTENDPRAUTO:append:rpi = "webosrpi5"

FILESEXTRAPATHS:prepend:rpi := "${THISDIR}/${BPN}:"

SRC_URI:append:rpi = " \
    file://0001-hciuart-lower-the-speed-and-restart-on-failure.patch \
    file://btuart \
"

do_install:append:rpi() {
    install -m 0755 ${UNPACKDIR}/btuart ${D}${bindir}
    rm -vf ${D}${systemd_system_unitdir}/bthelper@.service
    rm -vf ${D}${bindir}/bthelper
    rm -vrf ${D}${sysconfdir}
}

FILES:${PN}:rpi = "\
    ${bindir} \
"

SYSTEMD_SERVICE:${PN}:remove:rpi = "bthelper@.service"
