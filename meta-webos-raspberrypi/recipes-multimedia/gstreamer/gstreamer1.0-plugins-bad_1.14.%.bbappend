# Copyright (c) 2018-2019 LG Electronics, Inc.

require gstreamer1.0-webos-common.inc

EXTENDPRAUTO_append_rpi = "webosrpi2"

PACKAGECONFIG_append_rpi = " kms wayland"

#removed for license, see https://gpro.lgsvl.com/#/c/213604/7/meta-webos-raspberrypi/recipes-multimedia/g-media-pipeline/g-media-pipeline.bb
PACKAGECONFIG_remove_rpi = "faad"

WEBOS_REPO_NAME_rpi = "gst-plugins-bad"

WEBOS_VERSION_rpi = "1.14.4-3_9c24b4b7e54d11eedd7c80b1a3e33313a9583e60"
