# Copyright (c) 2018-2020 LG Electronics, Inc.

EXTENDPRAUTO_append_rpi = "webosrpi2"

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

SRC_URI_append_raspberrypi4 = " \
    file://0001-v4l2-fix-buffer-pool-poll-wait-after-flush.patch \
    file://0002-v4l2videodec-Check-stop-in-flush-to-avoid-race-condi.patch \
"

PACKAGECONFIG_append_raspberrypi4 = " libv4l2"
