# Copyright (c) 2020-2025 LG Electronics, Inc.

EXTENDPRAUTO:append:rpi = "webosrpi9"

FILESEXTRAPATHS:prepend:rpi := "${THISDIR}/${BPN}:"

SRC_URI:append:rpi = " \
    file://0001-implement-drm_create_linear_prime_buffer.patch \
"
