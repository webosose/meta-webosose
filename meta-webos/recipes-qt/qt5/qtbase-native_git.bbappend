# Copyright (c) 2017-2020 LG Electronics, Inc.

inherit webos_qmake5_base

EXTENDPRAUTO_append = "webos6"

QT_CONFIG_FLAGS_remove = "-no-gui -no-libpng"
QT_CONFIG_FLAGS_append = " -no-widgets -evdev"

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

# Upstream-Status: Backport
SRC_URI_append = " \
    file://0001-Do-not-ignore-exit-codes-of-install-commands.patch \
    file://0002-Do-not-ignore-exit-codes-when-installing-meta-files.patch \
"

# Upstream-Status: Inappropriate
SRC_URI_append = " \
    file://9901-Add-webos-oe-g-and-webos-oe-clang-platforms.patch \
"
