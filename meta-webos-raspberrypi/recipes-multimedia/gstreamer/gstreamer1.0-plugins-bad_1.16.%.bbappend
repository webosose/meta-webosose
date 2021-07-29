# Copyright (c) 2018-2022 LG Electronics, Inc.

EXTENDPRAUTO:append:rpi = "webosrpi7"

FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

DEPENDS:append:rpi = " webos-wayland-extensions"

PACKAGECONFIG:append:rpi = " kms wayland"

PACKAGECONFIG:remove:rpi = "faad"

SRC_URI:append:rpi = " \
    file://0001-waylandsink-remove-unsupported-subcompositor.patch \
    file://0001-rtpmanagerbad-add-RTP-streaming-elements.patch \ 
"
