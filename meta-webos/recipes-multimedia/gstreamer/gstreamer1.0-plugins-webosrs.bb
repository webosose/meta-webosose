# Copyright (c) 2023-2025 LG Electronics, Inc.

SUMMARY = "GStreamer Rust Plugins for webos"

inherit webos_public_repo webos_enhanced_submissions pkgconfig
require gstreamer1.0-plugins-webosrs.inc

PR = "${INC_PR}.2"

PACKAGECONFIG = "unifiedsinkbin"
PACKAGECONFIG[unifieddecodebin] = '--cfg feature=\"unifieddecodebin\"'
PACKAGECONFIG[unifiedsinkbin] = '--cfg feature=\"unifiedsinkbin\"'

VIDEOSINK ?= "waylandsink"
PACKAGECONFIG:append = " ${VIDEOSINK}"
PACKAGECONFIG[waylandsink] = '--cfg feature=\"waylandsink\"'

VIDEOCONVERT ?= "videoconvert"
PACKAGECONFIG:append = " ${VIDEOCONVERT}"
PACKAGECONFIG[videoconvert] = '--cfg feature=\"videoconvert\"'

RUSTFLAGS:append = " ${PACKAGECONFIG_CONFARGS}"
