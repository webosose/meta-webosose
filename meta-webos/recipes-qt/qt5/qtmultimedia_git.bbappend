# Copyright (c) 2016-2020 LG Electronics, Inc.

inherit webos_qmake5

EXTENDPRAUTO_append = "webos5"

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

# Patches based on 5.12.meta-qt5.2
SRC_URI += " \
    file://0001-Add-QMediaPlayerControl-accessor.patch \
"
