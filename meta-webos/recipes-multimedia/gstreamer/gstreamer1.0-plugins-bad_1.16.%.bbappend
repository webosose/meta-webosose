# Copyright (c) 2018-2022 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos3"

PACKAGECONFIG:remove = "rsvg"

# Needed only for qemux86 build
# FILESEXTRAPATHS:prepend:qemux86 := "${THISDIR}/${BPN}:"
# SRC_URI:append:qemux86 = " file://0002-render-into-wl_surface-without-sub-surface.patch"

FILESEXTRAPATHS:prepend:qemux86-64 := "${THISDIR}/${BPN}:"
SRC_URI:append:qemux86-64 = " \
    file://0001_set_initial_window_size.patch \
    file://0002_add_video_info_message.patch  \
    file://0003_disable_using_subsurface_subcompositor.patch \
    file://0004-rtpmanagerbad-add-RTP-streaming-elements.patch \
"
