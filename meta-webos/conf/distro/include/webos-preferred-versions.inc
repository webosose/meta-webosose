# Copyright (c) 2012-2020 LG Electronics, Inc.

PREFERRED_VERSION_webruntime ?= "72.%"

# Prefer GStreamer version 1.14.% for rpi3
GSTREAMER_VERSION = "1.14.%"

# Use GStreamer version 1.16.0 for raspberrypi4
GSTREAMER_VERSION_raspberrypi4 = "1.16.0"

PREFERRED_VERSION_gstreamer1.0              ?= "${GSTREAMER_VERSION}"
PREFERRED_VERSION_gstreamer1.0-plugins-bad  ?= "${GSTREAMER_VERSION}"
PREFERRED_VERSION_gstreamer1.0-plugins-base ?= "${GSTREAMER_VERSION}"
PREFERRED_VERSION_gstreamer1.0-plugins-good ?= "${GSTREAMER_VERSION}"
PREFERRED_VERSION_gstreamer1.0-plugins-ugly ?= "${GSTREAMER_VERSION}"
PREFERRED_VERSION_gstreamer1.0-libav        ?= "${GSTREAMER_VERSION}"
PREFERRED_VERSION_gstreamer1.0-omx          ?= "${GSTREAMER_VERSION}"
