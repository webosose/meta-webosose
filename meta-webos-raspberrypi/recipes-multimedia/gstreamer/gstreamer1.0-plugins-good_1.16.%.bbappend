# Copyright (c) 2020 LG Electronics, Inc.

EXTENDPRAUTO_append_rpi = "webosrpi1"

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

SRC_URI_append_raspberrypi4 = " \
    file://0001-v4l2-fix-buffer-pool-poll-wait-after-flush.patch \
    file://0002-v4l2bufferpool-return-TRUE-when-buffer-pool-orphanin.patch \
    file://0003-v4l2bufferpool-Free-orphaned-allocator-resources-whe.patch \
    file://0004-v4l2object-Orphan-buffer-pool-on-object_stop-if-supp.patch \
    file://0005-v4l2videodec-Fix-drain-function-return-type.patch \
    file://0006-v4l2videodec-ensure-pool-exists-before-orphaning-it.patch \
    file://0007-v4l2videodec-Check-stop-in-flush-to-avoid-race-condi.patch \
"

PACKAGECONFIG_append_raspberrypi4 = " libv4l2"
