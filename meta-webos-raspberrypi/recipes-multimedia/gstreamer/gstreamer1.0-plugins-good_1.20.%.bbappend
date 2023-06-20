# Copyright (c) 2018-2023 LG Electronics, Inc.

EXTENDPRAUTO:append:rpi = "webosrpi5"

FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

SRC_URI:append:raspberrypi4 = " \
    file://0002-Fix-v4l2h264dec-output-caps-to-RGB16.patch;striplevel=3 \
    file://0003-v4l2videodec-refactor-the-setup-process-of-capture.patch \
    file://0004-v4l2videodec-enable-resolution-change.patch \
"

PACKAGECONFIG:append:raspberrypi4 = " libv4l2"
