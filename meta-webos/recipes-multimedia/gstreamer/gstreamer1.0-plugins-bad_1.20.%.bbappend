# Copyright (c) 2018-2022 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos5"

PACKAGECONFIG:remove = "rsvg"

FILESEXTRAPATHS:prepend:qemux86-64 := "${THISDIR}/${BPN}:"
SRC_URI:append:qemux86-64 = " \
    file://0005-waylandsink-remove-unsupported-subcompositor.patch \
"
