# Copyright (c) 2018 LG Electronics, Inc.

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

EXTENDPRAUTO_append = "webos5"

SRC_URI += " \
    file://0004-Support-WPS-PBC-and-PIN-mode.patch \
    file://0006-Fix-Unable-to-reconnect-to-same-Wi-Fi.patch \
    file://0007-Fix-for-wifi-network-switching-and-unable-to-connect.patch \
"
