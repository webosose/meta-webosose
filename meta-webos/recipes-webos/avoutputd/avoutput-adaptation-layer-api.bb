# Copyright (c) 2017-2024 LG Electronics, Inc.

SUMMARY = "AVOutputd adaptation layer (AVAL) API definition and test harness"
SECTION = "webos/libs"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=89aea4e17d99a7cacdbeed46a0096b10"

DEPENDS = "glib-2.0"

WEBOS_VERSION = "1.0.0-2_702559d1cf19c68015f4a9d89ede358bc039dbb2"
PR = "r1"

inherit webos_component
inherit webos_pkgconfig
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_public_repo

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"
