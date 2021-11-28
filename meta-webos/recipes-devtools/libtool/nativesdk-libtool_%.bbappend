# Copyright (c) 2021 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos2"

FILESEXTRAPATHS_prepend := "${THISDIR}/libtool:"
SRC_URI += "file://0001-libtool-fix-clang-linking-with-fsanitize-address-or.patch"
