# Copyright (c) 2020 LG Electronics, Inc.

EXTENDPRAUTO_append = "webosrpi1"

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

SRC_URI_append_webos = " \
     file://0001-Disable-4-way-handshake-offloading-for-WPA-WPA2-PSK.patch \
"
