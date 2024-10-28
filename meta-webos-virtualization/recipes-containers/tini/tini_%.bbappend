# Copyright (c) 2024 LG Electronics, Inc.

EXTENDPRAUTO:append = "webosvirt1"

FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

SRC_URI += "file://0001-CMakeLists.txt-Replace-fstack-protector-by-fstack-pr.patch"
