# Copyright (c) 2018-2023 LG Electronics, Inc.

EXTENDPRAUTO:append:rpi = "webosrpi7"

FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

DEPENDS:append:rpi = " webos-wayland-extensions"

PACKAGECONFIG:append:rpi = " kms wayland"

PACKAGECONFIG:remove:rpi = "faad"

SRC_URI:append:rpi = " \
      file://0005-waylandsink-remove-unsupported-subcompositor.patch \
      file://0006-h264parse-resolution-changed-event-support.patch \
    "
