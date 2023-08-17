# Copyright (c) 2023 LG Electronics, Inc.

SUMMARY = "GStreamer Rust Plugins for webos"

inherit webos_public_repo webos_enhanced_submissions pkgconfig
require gstreamer1.0-plugins-webosrs.inc

PR = "${INC_PR}.0"
