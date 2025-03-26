# Copyright (c) 2018-2025 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos2"

FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

SRC_URI:append = " \
    file://0001-highgui-Wayland-xdg_shell.patch \
    file://0002-Add-missing-header-for-LIBAVCODEC_VERSION_INT.patch \
"
