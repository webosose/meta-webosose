# Copyright (c) 2019 LG Electronics, Inc.

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

EXTENDPRAUTO_append = "webosrpi1"

inherit systemd
SYSTEMD_SERVICE_${PN} = "\
    hciuart.service \
"

SRC_URI += " \
    file://hciuart.service \
    file://btuart \
"

inherit allarch

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/btuart ${D}${bindir}

    if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
        install -d ${D}${systemd_system_unitdir}
        install -m 0644 ${WORKDIR}/hciuart.service ${D}${systemd_system_unitdir}/hciuart.service
    fi
}

FILES_${PN} = "\
    ${bindir} \
    ${sysconfdir} \
    ${systemd_unitdir}/system \
"
