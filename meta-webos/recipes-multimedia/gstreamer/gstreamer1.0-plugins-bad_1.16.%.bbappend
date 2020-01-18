# Copyright (c) 2018-2020 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos2"

PACKAGECONFIG_remove = "rsvg"

# Needed only for qemux86 build
# FILESEXTRAPATHS_prepend_qemux86 := "${THISDIR}/${BPN}:"
# SRC_URI_append_qemux86 = " file://0002-render-into-wl_surface-without-sub-surface.patch"
