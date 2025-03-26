# Copyright (c) 2023-2025 LG Electronics, Inc.

EXTENDPRAUTO:append:rpi = "webosrpi1"

# gst omx is used only for raspberrypi builds
VIRTUAL-RUNTIME_media:append:rpi = " \
    gstreamer1.0-omx \
"
# Until build issues caused by PLAT-44962 are fixed in PLAT-45700
VIRTUAL-RUNTIME_media:raspberrypi3-64 = ""

RDEPENDS:${PN}:append:rpi = " \
    ${VIRTUAL-RUNTIME_media} \
"
