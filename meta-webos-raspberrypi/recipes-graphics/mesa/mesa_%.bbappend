# Copyright (c) 2020 LG Electronics, Inc.

EXTENDPRAUTO:append = "webosrpi4"

FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

SRC_URI:append:rpi = " \
    file://0001-implement-drm_create_linear_prime_buffer.patch \
"

# Backport this change from meta-raspberrypi:
# https://github.com/agherzan/meta-raspberrypi/commit/c5633df78832dca24cd584a10f896689b824b84d
SRC_URI:remove:rpi = " \
    file://0001-dri2-query-dma-buf-modifiers.patch \
"
