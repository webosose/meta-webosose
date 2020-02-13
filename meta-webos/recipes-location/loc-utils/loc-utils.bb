# Copyright (c) 2020 LG Electronics, Inc.

DESCRIPTION = "Utility library used in Location Framework"
AUTHOR = "vibhanshu.dhote <vibhanshu.dhote@lge.com>"
SECTION = "webos/location"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

DEPENDS = "glib-2.0 curl pmloglib"

WEBOS_VERSION = "1.0.0-10_c4cc493ef11f88386e98f783046ae86e7fae472e"
PR = "r1"

inherit webos_component
inherit webos_public_repo
inherit webos_enhanced_submissions
inherit webos_cmake

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"
