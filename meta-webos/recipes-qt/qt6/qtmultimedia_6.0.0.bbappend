# Copyright (c) 2016-2021 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos8"

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

# Patches based on 5.12.meta-qt5.2
SRC_URI += " \
    file://0001-Add-QMediaPlayerControl-accessor.patch \
"
