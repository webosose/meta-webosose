# Copyright (c) 2013-2018 LG Electronics, Inc.

SUMMARY = "libwebosi18n library can be used by non-QT C++ components for localization"
AUTHOR = "Edwin Hoogerbeets <edwin.hoogerbeets@lge.com>"
SECTION = "webos/libs"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

DEPENDS = "libpbnjson boost"

WEBOS_VERSION = "1.0.1-1_274a2e64f54095f3a95c06d2133c86b180592b76"
PR = "r1"

inherit webos_component
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_library
inherit webos_public_repo

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"
