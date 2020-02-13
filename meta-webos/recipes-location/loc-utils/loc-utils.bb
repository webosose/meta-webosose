# Copyright (c) 2020 LG Electronics, Inc.

DESCRIPTION = "Utility library used in Location Framework"
AUTHOR = "vibhanshu.dhote <vibhanshu.dhote@lge.com>"
SECTION = "webos/location"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

DEPENDS = "glib-2.0 curl glibcurl pmloglib"

WEBOS_VERSION = "1.0.0-9_70c3e7aa5393b2be8c06e340bd7b712e3d17fb6b"
PR = "r0"

inherit webos_component
inherit webos_public_repo
inherit webos_enhanced_submissions
inherit webos_cmake

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"
