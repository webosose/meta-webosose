# Copyright (c) 2020 LG Electronics, Inc.

EXTENDPRAUTO_append = "webosrpi3"

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

SRC_URI_append_rpi = " \
    file://0001-implement-wl-drm-create-linear-prime-buffer.patch \
    file://0002-fix-multicolored-tile-issue.patch \
"
