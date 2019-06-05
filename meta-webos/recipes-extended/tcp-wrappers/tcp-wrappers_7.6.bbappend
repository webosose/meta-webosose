# Copyright (c) 2019 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos1"

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

# PLAT-79213: Fix an error in do_compile with -Werror=return-type
SRC_URI += "file://fix-compile.patch"
