# Copyright (c) 2020 LG Electronics, Inc.

EXTENDPRAUTO_append = "webosrpi2"

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

SRC_URI_append_rpi = " \
    file://0001-add-the-wayland-drm-protocol.patch \
    file://0002-add-create-linear-prime-buffer.patch \
"
