# Copyright (c) 2018-2024 LG Electronics, Inc.

EXTENDPRAUTO:append:rpi = "webosrpi6"

FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

SRC_URI:append:rpi = " \
    file://0002-Fix-v4l2h264dec-output-caps-to-RGB16.patch;striplevel=3 \
    file://0003-v4l2videodec-refactor-the-setup-process-of-capture.patch \
    file://0004-v4l2videodec-enable-resolution-change.patch \
"

PACKAGECONFIG:append:rpi = " libv4l2"
