# Copyright (c) 2023 LG Electronics, Inc.

SUMMARY = "GStreamer plugin for webOS"

require gstreamer1.0-plugins-common.inc
require gstreamer1.0-webos-common.inc

AUTHOR = "DongJoo Kim <dongjoo.kim@lge.com>"

LICENSE = "LGPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=6762ed442b3822387a51c92d928ead0d"

PR = "r2"
DEPENDS = "gstreamer1.0-plugins-base gstreamer1.0-plugins-bad pmloglib libxml2"

inherit gobject-introspection

# Dynamically generate packages for all enabled plugins
PACKAGES_DYNAMIC =+ "^libgst.*"

inherit webos_machine_actual_dep
inherit webos_public_repo

WEBOS_REPO_NAME = "gst-plugins-webos"
SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"

WEBOS_VERSION = "1.18.2-4_3a2d2ffe333c172d1d936f790d25a034add0ab59"

EXTRA_OEMESON = "\
    -Ddoc=disabled \
    -Dmachine=${MACHINE} \
"
