# Copyright (c) 2012-2018 LG Electronics, Inc.

PKGV .= "-0webos3"
EXTENDPRAUTO_append = "webos13"

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

SRC_URI += " \
    file://0001-Add-tzset-applet.patch \
    file://0002-date-add-support-for-options-U-and-S.patch \
    file://0003-libedit-check-for-null-before-passing-cmdedit_prompt.patch \
"

RPROVIDES_${PN} += "stat"
RPROVIDES_${PN} += "bash"
