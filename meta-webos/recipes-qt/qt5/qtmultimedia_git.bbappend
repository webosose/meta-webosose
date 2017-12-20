# Copyright (c) 2016-2018 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos2"

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

SRC_URI += " \
    file://0002-Add-QMediaPlayerControl-accessor.patch \
"
