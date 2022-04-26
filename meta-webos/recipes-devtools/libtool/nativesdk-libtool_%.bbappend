# Copyright (c) 2021-2022 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos2"

FILESEXTRAPATHS:prepend := "${THISDIR}/libtool:"
SRC_URI += "file://0001-libtool-fix-clang-linking-with-fsanitize-address-or.patch"
