# Copyright (c) 2018-2020 LG Electronics, Inc.

require gstreamer1.0-webos-common.inc

EXTENDPRAUTO:append:rpi = "webosrpi3"

WEBOS_REPO_NAME:rpi = "gstreamer"

FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

SRC_URI:append:rpi = " \
    file://0001-Add-support-for-seamless-seek-trickplay.patch \
    file://0001-elementfactory-add-GST-ELEMENT-FACTORY-TYPE-HARDWARE.patch \
"



