# Copyright (c) 2017-2019 LG Electronics, Inc.

EXTENDPRAUTO_append = "webosrpi2"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI_append_sota = " \
    file://0001-Speed-up-boot-by-disable-usb-and-enable-bootcount.patch \
"
