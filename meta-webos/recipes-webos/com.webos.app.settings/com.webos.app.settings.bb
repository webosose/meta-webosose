# Copyright (c) 2017-2021 LG Electronics, Inc.

SUMMARY = "General Settings application"
AUTHOR = "Anish TD <anish.td@lge.com>"
SECTION = "webos/apps"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10 \
file://oss-pkg-info.yaml;md5=82c76ba3ea1ed22d2b1b41add2fbdc19 \
"

WEBOS_VERSION = "1.1.0-14_4fb1b130dc1517083a33eb980b14074640385766"
PR = "r2"

inherit webos_component
inherit webos_enhanced_submissions
inherit webos_enactjs_app
inherit webos_public_repo

WEBOS_ENACTJS_SHRINKWRAP_OVERRIDE = "false"

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

WEBOS_ENACTJS_APP_ID = "com.palm.app.settings"
