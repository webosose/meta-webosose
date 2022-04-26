# Copyright (c) 2018-2022 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos2"

PACKAGECONFIG:remove = "rsvg"

# Needed only for qemux86 build
# FILESEXTRAPATHS:prepend:qemux86 := "${THISDIR}/${BPN}:"
# SRC_URI:append:qemux86 = " file://0002-render-into-wl_surface-without-sub-surface.patch"
