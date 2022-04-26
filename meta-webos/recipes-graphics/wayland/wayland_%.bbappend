# Copyright (c) 2015-2022 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos2"

FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

DEPENDS:append:class-target = " pmloglib"
EXTRA_OECONF:append:class-target = " --enable-pmlog"

SRC_URI += " \
    file://0001-server-Fix-crash-when-accessing-client-which-is-alre.patch \
    file://0002-client-Exit-on-a-fatal-connection-error.patch \
    file://0003-Enhance-Wayland-debug-print.patch \
"
