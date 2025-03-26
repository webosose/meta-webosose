# Copyright (c) 2015-2025 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos6"

FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

PACKAGECONFIG[pmlog] = "-Dpmlog=true,-Dpmlog=false,pmloglib"
PACKAGECONFIG:append:class-target = " pmlog"

SRC_URI += " \
    file://0001-server-Fix-crash-when-accessing-client-which-is-alre.patch \
    file://0002-client-Exit-on-a-fatal-connection-error.patch \
    file://0003-Enhance-Wayland-debug-print.patch \
"
