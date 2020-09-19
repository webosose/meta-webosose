# Copyright (c) 2020 LG Electronics, Inc.

EXTENDPRAUTO_append = "webosrpi4"

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

SRC_URI_append_rpi = " \
    file://0001-implement-drm_create_linear_prime_buffer.patch \
"

# Backport this change from meta-raspberrypi:
# https://github.com/agherzan/meta-raspberrypi/commit/c5633df78832dca24cd584a10f896689b824b84d
SRC_URI_remove_rpi = " \
    file://0001-dri2-query-dma-buf-modifiers.patch \
"
