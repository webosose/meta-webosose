# Copyright (c) 2022 LG Electronics, Inc.

SUMMARY = "Event Monitoring for com.webos.service.pdm service"
AUTHOR = "Manjuraehmad Momin <manjuraehmad.momin@lge.com>"
SECTION = "webos/base"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10 \
    file://oss-pkg-info.yaml;md5=6649690cace85a7f9ce2926f5b78b53a\
"

DEPENDS = "glib-2.0 event-monitor pmloglib libpbnjson libwebosi18n"

WEBOS_VERSION = "1.0.0-2_cb5f187468afdede91bf580d9f4ef6d2e073edcb"
PR = "r0"

inherit webos_component
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_pkgconfig
inherit webos_event_monitor_plugin
inherit webos_public_repo

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"
