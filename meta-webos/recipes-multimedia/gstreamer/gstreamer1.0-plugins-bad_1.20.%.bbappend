# Copyright (c) 2018-2022 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos4"

PACKAGECONFIG:remove = "rsvg"

FILESEXTRAPATHS:prepend:qemux86-64 := "${THISDIR}/${BPN}:"
SRC_URI:append:qemux86-64 = " \
    file://0001-set-initial-window-size.patch;striplevel=3 \
    file://0002-add-video-info-message.patch;striplevel=3 \
    file://0003-disable-using-subsurface-subcompositor.patch;striplevel=3 \
"
