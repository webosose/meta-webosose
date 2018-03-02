# Copyright (c) 2018 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos1"

FILESEXTRAPATHS_prepend := "${THISDIR}/gcc-6.2:"

SRC_URI_append = " file://0001-AArch64-Cleanup-mpc-relative-loads"
