# Copyright (c) 2012-2023 LG Electronics, Inc.

PKGV .= "-0webos3"
EXTENDPRAUTO:append = "webos14"

FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

SRC_URI += " \
    file://0001-Add-tzset-applet.patch \
    file://0002-date-add-support-for-options-U-and-S.patch \
    file://0003-libedit-check-for-null-before-passing-cmdedit_prompt.patch \
"

RPROVIDES:${PN} += "stat"
RPROVIDES:${PN} += "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"
RPROVIDES:${PN} += "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_TAR', 'busybox', 'tar', '', d)}"
