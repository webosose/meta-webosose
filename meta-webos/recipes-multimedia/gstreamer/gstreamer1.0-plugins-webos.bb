# Copyright (c) 2023 LG Electronics, Inc.

SUMMARY = "GStreamer plugin for webOS"

require gstreamer1.0-plugins-common.inc

AUTHOR = "DongJoo Kim <dongjoo.kim@lge.com>"

LICENSE = "LGPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=6762ed442b3822387a51c92d928ead0d"

PR = "r3"
DEPENDS = "gstreamer1.0-plugins-base"

inherit gobject-introspection

# Dynamically generate packages for all enabled plugins
PACKAGES_DYNAMIC =+ "^libgst.*"

inherit webos_public_repo
inherit webos_enhanced_submissions
inherit webos_machine_impl_dep

WEBOS_REPO_NAME = "gst-plugins-webos"
SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

WEBOS_VERSION = "1.18.2-6_7d108f95b8a6d7772b31fe9e07e05a2401c220e2"

EXTRA_OEMESON = "\
    -Ddoc=disabled \
    -Dmachine=${MACHINE} \
"
