# Copyright (c) 2023 LG Electronics, Inc.

SUMMARY = "GStreamer plugin for webOS"

require gstreamer1.0-plugins-common.inc
require gstreamer1.0-webos-common.inc

AUTHOR = "DongJoo Kim <dongjoo.kim@lge.com>"

LICENSE = "LGPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=6762ed442b3822387a51c92d928ead0d"

PR = "r3"
DEPENDS = "gstreamer1.0-plugins-base"

inherit gobject-introspection

# Dynamically generate packages for all enabled plugins
PACKAGES_DYNAMIC =+ "^libgst.*"

inherit webos_public_repo

WEBOS_REPO_NAME = "gst-plugins-webos"
SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"

WEBOS_VERSION = "1.18.2-5_84ad839588752cbf240f2f9a4d707b0699cc1af5"

EXTRA_OEMESON = "\
    -Ddoc=disabled \
    -Dmachine=${MACHINE} \
"
