# Copyright (c) 2019-2020 LG Electronics, Inc.

EXTENDPRAUTO_append = "webosrpi3"

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"
SRC_URI += "\
    file://0001-hciuart-lower-the-speed-and-restart-on-failure.patch \
    file://btuart \
"

do_install_append() {
    install -m 0755 ${WORKDIR}/btuart ${D}${bindir}
    rm -vf ${D}${systemd_system_unitdir}/bthelper@.service
    rm -vf ${D}${bindir}/bthelper
    rm -vrf ${D}${sysconfdir}
}

FILES_${PN} = "\
    ${bindir} \
"

SYSTEMD_SERVICE_${PN}_remove = "bthelper@.service"
