# Copyright (c) 2015-2020 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos2"

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

DEPENDS_append_class-target = " pmloglib"
EXTRA_OECONF_append_class-target = " --enable-pmlog"

SRC_URI += " \
    file://0001-server-Fix-crash-when-accessing-client-which-is-alre.patch \
    file://0002-client-Exit-on-a-fatal-connection-error.patch \
    file://0003-Enhance-Wayland-debug-print.patch \
"
