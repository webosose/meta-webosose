# Copyright (c) 2018 LG Electronics, Inc.

SUMMARY = "Service which controls audio output"
AUTHOR = "Premalatha MVS <premalatha.mvs@lge.com>"
SECTION = "webos/services"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://CMakeLists.txt;beginline=1;endline=15;md5=232fd0174eef9aa036a9c08ff983b67b"

DEPENDS = "glib-2.0 luna-service2 pmloglib libpbnjson umi"

inherit webos_component
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_daemon
inherit webos_library
inherit webos_system_bus
inherit webos_public_repo

WEBOS_VERSION = "1.0.0-1_5732e885fd97103310874656887a6a34389af590"
PR = "r0"

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"
