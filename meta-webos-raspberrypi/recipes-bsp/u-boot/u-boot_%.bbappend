# Copyright (c) 2017 LG Electronics, Inc.

EXTENDPRAUTO_append = "webosrpi1"

# remove patches from meta-raspberrypi meant to be used with 2016.03
SRC_URI_remove_rpi = " \
    file://0001-arm-add-save_boot_params-for-ARM1176.patch \
    file://0002-rpi-passthrough-of-the-firmware-provided-FDT-blob.patch \
    file://0003-Include-lowlevel_init.o-for-rpi2.patch \
"
