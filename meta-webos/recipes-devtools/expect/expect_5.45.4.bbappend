# Copyright (c) 2019-2024 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos2"

FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

# PLAT-79213: Fix an error in do_compile with -Werror=return-type
SRC_URI += "file://fix-compile.patch"

# http://gecko.lge.com:8000/Errors/Details/821861
# expect5.45.4/exp_chan.c:62:5: error: initialization of 'struct Tcl_ChannelTypeVersion_ *' from incompatible pointer type 'int (*)(void *, int)' [-Wincompatible-pointer-types]
CFLAGS += "-Wno-error=incompatible-pointer-types"
