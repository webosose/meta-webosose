# Copyright (c) 2018-2024 LG Electronics, Inc.

EXTENDPRAUTO:append:rpi = "webosrpi4"

WEBOS_REPO_NAME:rpi = "gstreamer"

# 0001-Add-support-for-seamless-seek-trickplay.patch is from meta-webos layer
SRC_URI:append:rpi = " \
    file://0001-Add-support-for-seamless-seek-trickplay.patch \
"
