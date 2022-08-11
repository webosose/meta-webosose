# Copyright (c) 2022 LG Electronics, Inc.

SUMMARY = "Video Call Application"
AUTHOR = "Ganesh Bhat<ganesh.bhat@lge.com>"
SECTION = "webos/apps"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://LICENSE;md5=86d3f3a95c324c9479bd8986968f4327 \
    file://oss-pkg-info.yaml;md5=0ec407cd2d4a192e0c60888f4ec66dd7 \
"

WEBOS_VERSION = "0.0.1-2_74b3bacbd9ae4d5a8daba53425a109e3de4e3458"
PR = "r0"


inherit webos_enhanced_submissions
inherit webos_enactjs_app
inherit webos_public_repo
inherit webos_localizable


SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

WEBOS_ENACTJS_APP_ID = "com.webos.app.videocall"
