# Copyright (c) 2019-2024 LG Electronics, Inc.

EXTENDPRAUTO:append = "webosrpi4"

FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"
SRC_URI += "\
    file://0001-hciuart-lower-the-speed-and-restart-on-failure.patch \
    file://btuart \
"

do_install:append() {
    install -m 0755 ${UNPACKDIR}/btuart ${D}${bindir}
    rm -vf ${D}${systemd_system_unitdir}/bthelper@.service
    rm -vf ${D}${bindir}/bthelper
    rm -vrf ${D}${sysconfdir}
}

FILES:${PN} = "\
    ${bindir} \
"

SYSTEMD_SERVICE:${PN}:remove = "bthelper@.service"
