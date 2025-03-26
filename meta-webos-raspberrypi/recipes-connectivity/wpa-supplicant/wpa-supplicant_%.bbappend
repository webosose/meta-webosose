# Copyright (c) 2020-2024 LG Electronics, Inc.

EXTENDPRAUTO:append:rpi = "webosrpi2"

FILESEXTRAPATHS:prepend:rpi := "${THISDIR}/${BPN}:"

SRC_URI:append:rpi = " \
     file://0001-Disable-4-way-handshake-offloading-for-WPA-WPA2-PSK.patch \
"
