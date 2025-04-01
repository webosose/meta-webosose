# Copyright (c) 2017-2025 LG Electronics, Inc.

EXTENDPRAUTO:append:rpi = "webosrpi6"

FILESEXTRAPATHS:prepend:rpi := "${THISDIR}/${BPN}:"

SRC_URI:append:rpi = " \
    file://boot.cfg \
"
