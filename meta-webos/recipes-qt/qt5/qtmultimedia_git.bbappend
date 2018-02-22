# Copyright (c) 2016-2019 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos4"

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

# Patches based on 5.11.meta-qt5.6
SRC_URI += " \
    file://0001-Add-QMediaPlayerControl-accessor.patch \
"
