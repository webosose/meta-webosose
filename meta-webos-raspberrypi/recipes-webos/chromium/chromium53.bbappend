# Copyright (c) 2018 LG Electronics, Inc.

EXTENDPRAUTO_append = "webosrpi1"

# use g-media-pipeline interface for rpi builds
DEPENDS_append_rpi = " g-media-pipeline"
GYP_DEFINES_append_rpi = " use_gst_media=1"

# hardware playback is still broken for aarch64
DEPENDS_remove_aarch64 = "g-media-pipeline"
GYP_DEFINES_remove_aarch64 = "use_gst_media=1"
