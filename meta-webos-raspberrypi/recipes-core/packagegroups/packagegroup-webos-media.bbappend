# Copyright (c) 2023-2025 LG Electronics, Inc.

EXTENDPRAUTO:append:rpi = "webosrpi2"

# gst omx is used only for raspberrypi builds
VIRTUAL-RUNTIME_media:rpi = "gstreamer1.0-omx"

RDEPENDS:${PN}:append:rpi = " \
    ${VIRTUAL-RUNTIME_media} \
"
