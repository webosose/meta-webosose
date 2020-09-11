# Copyright (c) 2020 LG Electronics, Inc.

EXTENDPRAUTO_append_rpi = "webosrpi7"

PACKAGECONFIG_remove_raspberrypi3 = "gstreamer umediaserver neva-media gav"
PACKAGECONFIG_remove_raspberrypi3-64 = "gstreamer umediaserver neva-media gav"

PACKAGECONFIG_NEVA_WEBRTC ?= "neva-webrtc"
PACKAGECONFIG_append_raspberrypi4 = " ${PACKAGECONFIG_NEVA_WEBRTC}"
