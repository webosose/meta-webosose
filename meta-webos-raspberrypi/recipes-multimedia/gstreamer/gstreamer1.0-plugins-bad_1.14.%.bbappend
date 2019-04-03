# Copyright (c) 2018-2019 LG Electronics, Inc.

require gstreamer1.0-webos-common.inc

EXTENDPRAUTO_append_rpi = "webosrpi2"

PACKAGECONFIG_append_rpi = " kms wayland faad"

WEBOS_REPO_NAME_rpi = "gst-plugins-bad"

WEBOS_VERSION_rpi = "1.14.0-2_850cf2243bf999481b2badc4a939f8fbda8e4e8d"
