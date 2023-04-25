# Copyright (c) 2017-2023 LG Electronics, Inc.

EXTENDPRAUTO:append = "webosrpi5"

FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

SRC_URI:append = " \
    file://0001-Speed-up-boot-by-disable-usb-and-enable-bootcount.patch \
"
