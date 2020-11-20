# Copyright (c) 2021 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos2"

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

PATCHTOOL = "git"

SRC_URI += " \
    file://0001-Implement-DropShadow-with-GaussianBlur.patch \
"
