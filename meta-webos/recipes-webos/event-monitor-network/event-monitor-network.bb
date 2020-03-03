# Copyright (c) 2017-2020 LG Electronics, Inc.

SUMMARY = "Event Monitoring for Network service"
AUTHOR = "Seokhee Lee <seokhee.lee@lge.com>"
SECTION = "webos/base"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

DEPENDS = "glib-2.0 event-monitor pmloglib libpbnjson libwebosi18n"

WEBOS_VERSION = "1.0.0-3_56fa4487982a448e0261d436bad63bbe7eb16a4d"
PR = "r0"

inherit webos_component
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_event_monitor_plugin
inherit webos_public_repo

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"
