# Copyright (c) 2014-2024 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos6"

PACKAGES =+ "${PN}-gpl"
LICENSE += "& GPL-2.0-only"
LICENSE:${PN}-gpl = "GPL-2.0-only"

RDEPENDS:${PN} += "${PN}-gpl"

FILES:${PN}-gpl = " \
    ${bindir}/dbus-cleanup-sockets \
    ${bindir}/dbus-daemon \
    ${bindir}/dbus-monitor \
    ${bindir}/dbus-send \
    ${bindir}/dbus-uuidgen \
"

VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS:${PN}-ptest:append:class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS:${PN}-ptest:remove:class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"
