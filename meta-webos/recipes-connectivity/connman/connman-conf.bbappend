# Copyright (c) 2017-2020 LG Electronics, Inc.

FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

EXTENDPRAUTO:append = "webos3"

SRC_URI += " \
    file://connman.service \
    file://connman.sh.in \
    file://main.conf \
"
do_configure:append () {
    sed -e 's/@WEBOS_CONNMAN_PREACTIVATE_INTERFACE_LIST@/${WEBOS_CONNMAN_PREACTIVATE_INTERFACE_LIST}/g; s%@DATADIR@%${datadir}%g' \
    ${WORKDIR}/connman.sh.in > ${WORKDIR}/connman.sh
}

do_install:append() {
    install -d ${D}${sysconfdir}/connman
    install -m 0644 ${WORKDIR}/main.conf ${D}${sysconfdir}/connman/

    install -d ${D}${sysconfdir}/systemd/system
    install -v -m 644 ${WORKDIR}/connman.service ${D}${sysconfdir}/systemd/system/
    install -d ${D}${sysconfdir}/systemd/system/scripts
    install -v -m 755 ${WORKDIR}/connman.sh ${D}${sysconfdir}/systemd/system/scripts/
}

FILES:${PN} += " \
    ${sysconfdir}/connman \
    ${sysconfdir} \
"
