# Copyright (c) 2021 LG Electronics, Inc.

EXTENDPRAUTO_append_rpi = "webosrpi1"

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

PACKAGECONFIG_append_rpi = " dmabuf-client-buffer"

# Upstream-Status: Inappropriate
SRC_URI_append_rpi = " \
    file://0001-Fix-build-error-when-finding-libdrm.patch \
"
