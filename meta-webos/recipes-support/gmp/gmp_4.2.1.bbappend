# Copyright (c) 2020 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos1"

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

# PLAT-79213: Fix an error in do_configure with -Werror=return-type
SRC_URI += "file://fix-configure.patch"

