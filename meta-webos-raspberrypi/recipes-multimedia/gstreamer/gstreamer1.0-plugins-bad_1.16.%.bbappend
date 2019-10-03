# Copyright (c) 2020 LG Electronics, Inc.

EXTENDPRAUTO_append_rpi = "webosrpi1"

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

DEPENDS_append_rpi = " webos-wayland-extensions"

PACKAGECONFIG_append_rpi = " kms wayland"

PACKAGECONFIG_remove_rpi = "faad"

SRC_URI_append_rpi = " \
    file://0001-waylandsink-remove-unsupported-subcompositor.patch \
    file://0002-waylandsink-zero-copy-using-wl-drm-protocol.patch \
"
