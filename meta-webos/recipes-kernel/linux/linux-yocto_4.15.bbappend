# Copyright (c) 2015-2017 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos5"

# Assign to PE, because empty PKGE in KERNEL_IMAGE_BASE_NAME causes two hyphens.
PE = "1"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append_qemux86 = " file://crypto.cfg \
                           file://my_gfx.cfg \
                           file://sound.cfg \
                           file://enable_uinput.cfg \
                           file://network_testing.cfg \
                           file://0000-change-keymax-value.patch \
"
