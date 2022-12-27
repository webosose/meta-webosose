# Copyright (c) 2015-2023 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos4"

FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

PACKAGECONFIG[pmlog] = "-Dpmlog=true,-Dpmlog=false,pmloglib"
PACKAGECONFIG:append:class-target = " pmlog"

SRC_URI += " \
    file://0001-server-Fix-crash-when-accessing-client-which-is-alre.patch \
    file://0002-client-Exit-on-a-fatal-connection-error.patch \
    file://0003-connection-Make-wl_closure_print-output-atomic.patch \
    file://0004-Enhance-Wayland-debug-print.patch \
"
