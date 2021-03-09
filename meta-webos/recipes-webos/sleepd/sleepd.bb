# Copyright (c) 2012-2021 LG Electronics, Inc.

SUMMARY = "Sleep scheduling policy daemon"
AUTHOR = "Sapna Todwal <sapna.todwal@lge.com>"
SECTION = "webos/base"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10 \
file://oss-pkg-info.yaml;md5=2bdfe040dcf81b4038370ae96036c519 \
"

DEPENDS = "nyx-lib luna-service2 json-c libxml2 sqlite3 glib-2.0"

WEBOS_VERSION = "2.0.0-11_aaceccf6d0ccb2930554b0cd69bfa5d802f09f6c"
PR = "r9"

inherit webos_component
inherit webos_public_repo
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_daemon
inherit webos_system_bus

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

SRC_URI += "file://0001-config.h-rename-to-sleepd_config.h-to-make-sure-the-.patch"
