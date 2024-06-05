# Copyright (c) 2023-2024 LG Electronics, Inc.

SUMMARY = "GStreamer Rust Plugins for webos"

inherit webos_public_repo webos_enhanced_submissions pkgconfig
require gstreamer1.0-plugins-webosrs.inc

PR = "${INC_PR}.1"

PACKAGECONFIG = "unifiedsinkbin"
PACKAGECONFIG[unifieddecodebin] = '--cfg feature=\"unifieddecodebin\",,,'
PACKAGECONFIG[unifiedsinkbin] = '--cfg feature=\"unifiedsinkbin\",,,'

RUSTFLAGS:append = " ${PACKAGECONFIG_CONFARGS}"
