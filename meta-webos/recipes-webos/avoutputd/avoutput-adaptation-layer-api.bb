# Copyright (c) 2017-2018 LG Electronics, Inc.

SUMMARY = "AVOutputd adaptation layer (AVAL) API definition and test harness"
SECTION = "webos/libs"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

DEPENDS = "glib-2.0"

WEBOS_VERSION ="1.0.0-1_46828b6f12a027d70eab3e3ae720b3f8be1de261"
PR = "r0"

inherit webos_component
inherit webos_pkgconfig
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_public_repo

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"
