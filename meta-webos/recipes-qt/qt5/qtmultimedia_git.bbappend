# Copyright (c) 2016-2017 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos2"

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

SRC_URI += " \
    file://0001-Declare-videonode-dependency-on-Qt5MultimediaQuick_p.patch \
    file://0002-Add-QMediaPlayerControl-accessor.patch \
"
