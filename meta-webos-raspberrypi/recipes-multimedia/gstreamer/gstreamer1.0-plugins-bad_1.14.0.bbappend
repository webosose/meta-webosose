# Copyright (c) 2018 LG Electronics, Inc.

require gstreamer1.0-webos-common.inc

EXTENDPRAUTO_append_rpi = "webosrpi1"

PACKAGECONFIG_append_rpi = " kms wayland directfb faad"

PACKAGECONFIG_remove_rpi = "rsvg"
EXTRA_OECONF_remove_rpi = "--disable-dispmanx"

WEBOS_REPO_NAME_rpi = "gst-plugins-bad"

WEBOS_VERSION_rpi = "1.14.0-1_953c65fc4393ae9bc079fa926813bf5eabeb31fa"

EXTRA_OECONF += "WAYLAND_PROTOCOLS_SYSROOT_DIR=${STAGING_DIR_TARGET}"
