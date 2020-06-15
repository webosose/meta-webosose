# Copyright (c) 2019-2020 LG Electronics, Inc.

SUMMARY = "General System Launcher application"
AUTHOR = "Kiho Choi<kiho.choi@lge.com>"
SECTION = "webos/apps"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=86d3f3a95c324c9479bd8986968f4327"

WEBOS_VERSION = "0.1.0-10_8d5f76f0ee7019996cbb5586eac679b52b90c678"
SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"
PR = "r2"

inherit webos_enhanced_submissions
inherit webos_enactjs_app
inherit webos_public_repo
inherit webos_localizable

WEBOS_ENACTJS_SHRINKWRAP_OVERRIDE = "false"

WEBOS_ENACTJS_APP_ID = "com.webos.app.home"
WEBOS_LOCALIZATION_XLIFF_BASENAME = "home"
