# Copyright (c) 2018-2025 LG Electronics, Inc.

EXTENDPRAUTO:append:rpi = "webosrpi6"

FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

SRC_URI:append:rpi = " \
    file://0002-Fix-v4l2h264dec-output-caps-to-RGB16.patch;striplevel=3 \
"

PACKAGECONFIG:append:rpi = " libv4l2"
