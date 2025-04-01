# Copyright (c) 2021-2025 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos4"

FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

PATCHTOOL = "git"

SRC_URI += " \
    file://0001-Implement-DropShadow-with-GaussianBlur.patch \
    file://0002-CMake-Update-to-latest-qml-CMake-API.patch \
"
