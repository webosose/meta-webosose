# Copyright (c) 2019-2022 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos1"

FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

# PLAT-79213: Fix an error in do_compile with -Werror=return-type
SRC_URI += "file://fix-compile.patch"
