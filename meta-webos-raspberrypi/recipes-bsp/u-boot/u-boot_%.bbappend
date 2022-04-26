# Copyright (c) 2017-2022 LG Electronics, Inc.

EXTENDPRAUTO:append = "webosrpi3"

FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

SRC_URI:append:sota = " \
    file://0001-Speed-up-boot-by-disable-usb-and-enable-bootcount.patch \
"

SRC_URI:append:sota:raspberrypi4 = " \
    file://0002-Speed-up-boot-for-raspberrypi4.patch \
"
