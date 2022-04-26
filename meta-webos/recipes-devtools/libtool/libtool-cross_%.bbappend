# Copyright (c) 2021-2022 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos1"

FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"
SRC_URI += "file://0001-libtool-fix-clang-linking-with-fsanitize-address-or.patch"
