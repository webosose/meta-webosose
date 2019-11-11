# Copyright (c) 2017-2019 LG Electronics, Inc.

SUMMARY = "General Settings application"
AUTHOR = "Anish TD <anish.td@lge.com>"
SECTION = "webos/apps"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

WEBOS_VERSION = "1.1.0-8_a06f6619d3f2c0aa330f8c7471808e4e89623199"
PR = "r1"

inherit webos_component
inherit webos_enhanced_submissions
inherit webos_enactjs_app
inherit webos_public_repo

WEBOS_ENACTJS_SHRINKWRAP_OVERRIDE = "false"

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

WEBOS_ENACTJS_APP_ID = "com.palm.app.settings"
