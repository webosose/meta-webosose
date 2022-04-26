# Copyright (c) 2020-2022 LG Electronics, Inc.

EXTENDPRAUTO:append = "webosrpi2"

FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

SRC_URI:append:rpi = " \
    file://0001-add-the-wayland-drm-protocol.patch \
    file://0002-add-create-linear-prime-buffer.patch \
"
