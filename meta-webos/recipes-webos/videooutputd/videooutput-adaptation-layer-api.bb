# Copyright (c) 2018-2019 LG Electronics, Inc.

SUMMARY = "Vidououtputd Adaptation Layer (VAL) API definition and test harness"
SECTION = "webos/libs"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e3fc50a88d0a364313df4b21ef20c29e"

DEPENDS = "glib-2.0"

inherit webos_component
inherit webos_pkgconfig
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_public_repo

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

WEBOS_VERSION ="1.0.0-2_54bf815da38476038675d3c2d53ef9ccadbc84a0"
PR = "r0"

