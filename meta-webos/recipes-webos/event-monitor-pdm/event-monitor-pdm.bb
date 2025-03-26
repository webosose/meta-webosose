# Copyright (c) 2022-2025 LG Electronics, Inc.

SUMMARY = "Event Monitoring for com.webos.service.pdm service"
AUTHOR = "Rajesh Gopu I.V <rajeshgopu.iv@lge.com>"
SECTION = "webos/base"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57 \
    file://oss-pkg-info.yaml;md5=6649690cace85a7f9ce2926f5b78b53a\
"

DEPENDS = "glib-2.0 event-monitor pmloglib libpbnjson libwebosi18n"

WEBOS_VERSION = "1.0.0-9_40c3e5abeb420d0fece8b61a0ebe71c5be6ad140"
PR = "r2"

inherit webos_component
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_pkgconfig
inherit webos_event_monitor_plugin
inherit webos_public_repo
inherit webos_localizable

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

WEBOS_LOCALIZATION_INSTALL_RESOURCES = "true"
