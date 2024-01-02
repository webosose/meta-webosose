# Copyright (c) 2017-2024 LG Electronics, Inc.

FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

EXTENDPRAUTO:append = "webos4"

SRC_URI += " \
    file://main.conf \
"

inherit webos_systemd
WEBOS_SYSTEMD_SERVICE = "connman.service"
WEBOS_SYSTEMD_SCRIPT = "connman.sh.in"
WEBOS_SYSTEMD_REPLACE_OTHERS = "-DWEBOS_CONNMAN_PREACTIVATE_INTERFACE_LIST="${WEBOS_CONNMAN_PREACTIVATE_INTERFACE_LIST}" -DDATADIR="${datadir}""

do_install:append() {
    install -d ${D}${sysconfdir}/connman
    install -m 0644 ${WORKDIR}/main.conf ${D}${sysconfdir}/connman/
}

FILES:${PN} += " \
    ${sysconfdir}/connman \
    ${sysconfdir} \
"
