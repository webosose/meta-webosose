# Copyright (c) 2018-2022 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos6"

PACKAGECONFIG:remove = "rsvg"

FILESEXTRAPATHS:prepend:qemuall := "${THISDIR}/${BPN}:"
SRC_URI:append:qemuall = " \
    file://0005-waylandsink-remove-unsupported-subcompositor.patch \
    file://0006-h264parse-resolution-changed-event-support.patch \
"
