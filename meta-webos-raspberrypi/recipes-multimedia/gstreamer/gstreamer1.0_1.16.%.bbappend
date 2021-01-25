# Copyright (c) 2018-2022 LG Electronics, Inc.

require gstreamer1.0-webos-common.inc

EXTENDPRAUTO:append:rpi = "webosrpi4"

WEBOS_REPO_NAME:rpi = "gstreamer"

FILESEXTRAPATHS:prepend:rpi := "${THISDIR}/${BPN}:"

# 0001-Add-support-for-seamless-seek-trickplay.patch is from meta-webos layer
SRC_URI:append:rpi = " \
    file://0001-Add-support-for-seamless-seek-trickplay.patch \
    file://0001-elementfactory-add-GST-ELEMENT-FACTORY-TYPE-HARDWARE.patch \
"



