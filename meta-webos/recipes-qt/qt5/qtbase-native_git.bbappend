# Copyright (c) 2017-2020 LG Electronics, Inc.

inherit webos_qmake5_base

EXTENDPRAUTO_append = "webos4"

QT_CONFIG_FLAGS_remove = "-no-gui -no-libpng"
QT_CONFIG_FLAGS_append = " -no-widgets -evdev"

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

SRC_URI_append = " \
    file://0001-Add-webos-oe-g-and-webos-oe-clang-platforms.patch \
"
