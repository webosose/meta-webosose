# Copyright (c) 2021 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos1"

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"
SRC_URI += "file://0001-libtool-fix-clang-linking-with-fsanitize-address-or.patch"
