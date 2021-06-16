# Copyright (c) 2021 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos3"

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

PATCHTOOL = "git"

SRC_URI += " \
    file://0001-Implement-DropShadow-with-GaussianBlur.patch \
    file://0002-CMake-Update-to-latest-qml-CMake-API.patch \
"
