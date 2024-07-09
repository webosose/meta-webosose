# Copyright (c) 2016-2024 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos8"

FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

# Patches based on 5.12.meta-qt5.2
SRC_URI += " \
    file://0001-Add-QMediaPlayerControl-accessor.patch \
"
