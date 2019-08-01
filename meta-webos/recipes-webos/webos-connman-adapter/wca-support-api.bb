# Copyright (c) 2017-2019 LG Electronics, Inc.

SUMMARY = "webOS connman adapter support API"
AUTHOR = "Seokhee Lee <seokhee.lee@lge.com>"
SECTION = "webos/libs"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

DEPENDS = "libpbnjson luna-service2"

WEBOS_VERSION = "1.0.0-2_630d61afb2708fe00297cfa148a0f900ef73996a"
PR = "r1"

inherit webos_component
inherit webos_enhanced_submissions
inherit webos_pkgconfig
inherit webos_cmake
inherit webos_public_repo

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"
