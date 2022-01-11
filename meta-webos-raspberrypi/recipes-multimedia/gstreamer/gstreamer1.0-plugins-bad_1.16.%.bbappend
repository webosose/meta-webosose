# Copyright (c) 2018-2022 LG Electronics, Inc.

EXTENDPRAUTO_append_rpi = "webosrpi7"

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

DEPENDS_append_rpi = " webos-wayland-extensions"

PACKAGECONFIG_append_rpi = " kms wayland"

PACKAGECONFIG_remove_rpi = "faad"

SRC_URI_append_rpi = " \
    file://0001-waylandsink-remove-unsupported-subcompositor.patch \
    file://0001-rtpmanagerbad-add-RTP-streaming-elements.patch \ 
"
