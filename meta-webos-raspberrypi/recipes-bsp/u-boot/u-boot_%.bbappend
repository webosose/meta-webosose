# Copyright (c) 2017-2020 LG Electronics, Inc.

EXTENDPRAUTO_append = "webosrpi3"

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

SRC_URI_append_sota = " \
    file://0001-Speed-up-boot-by-disable-usb-and-enable-bootcount.patch \
"

SRC_URI_append_sota_raspberrypi4 = " \
    file://0002-Speed-up-boot-for-raspberrypi4.patch \
"
