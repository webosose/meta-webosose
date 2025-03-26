# Copyright (c) 2017-2025 LG Electronics, Inc.

SUMMARY = "Event Monitoring for Network service"
AUTHOR = "Muralidhar N <muralidhar.n@lge.com>"
SECTION = "webos/base"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10 \
    file://oss-pkg-info.yaml;md5=ea312253974af80f7c37202a514df9e6 \
"

DEPENDS = "glib-2.0 event-monitor pmloglib libpbnjson libwebosi18n"

WEBOS_VERSION = "1.0.0-5_c99401afd1464f8b74766696560bad84a10e5ab4"
PR = "r2"

inherit webos_component
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_pkgconfig
inherit webos_event_monitor_plugin
inherit webos_public_repo

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"
