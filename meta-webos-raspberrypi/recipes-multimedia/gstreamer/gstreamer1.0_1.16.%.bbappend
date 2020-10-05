# Copyright (c) 2018-2020 LG Electronics, Inc.

require gstreamer1.0-webos-common.inc

EXTENDPRAUTO_append_rpi = "webosrpi3"

WEBOS_REPO_NAME_rpi = "gstreamer"

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

SRC_URI_append_rpi = " \
    file://0001-Add-support-for-seamless-seek-trickplay.patch \
    file://0001-elementfactory-add-GST-ELEMENT-FACTORY-TYPE-HARDWARE.patch \
"



