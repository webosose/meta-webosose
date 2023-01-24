# Copyright (c) 2017-2023 LG Electronics, Inc.

SUMMARY = "General Settings application"
AUTHOR = "Revanth Kumar <revanth.kumar@lge.com>"
SECTION = "webos/apps"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://LICENSE;md5=86d3f3a95c324c9479bd8986968f4327 \
    file://oss-pkg-info.yaml;md5=82c76ba3ea1ed22d2b1b41add2fbdc19 \
"

WEBOS_VERSION = "1.1.0-32_05f112eec5457f2f63d13e5a4aec819ab181e22c"
PR = "r3"

inherit webos_component
inherit webos_enhanced_submissions
inherit webos_enactjs_app
inherit webos_public_repo

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

WEBOS_ENACTJS_APP_ID = "com.palm.app.settings"

# Workaround for network access issue during do_compile task
do_compile[network] = "1"
