# Copyright (c) 2015-2019 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos1"

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

DEPENDS_append_class-target = " pmloglib"
EXTRA_OECONF_append_class-target = " --enable-pmlog"

SRC_URI += " \
    file://0001-Support-dynamic-turning-on-off-wayland-debug.patch \
    file://0002-Support-log-redirection-for-wayland-debug.patch \
    file://0003-Enhance-logging-for-wayland-error-detection.patch \
    file://0004-server-Fix-crash-when-accessing-client-which-is-alre.patch \
    file://0005-client-Exit-on-a-fatal-connection-error.patch \
"
