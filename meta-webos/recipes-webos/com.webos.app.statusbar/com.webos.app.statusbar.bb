# Copyright (c) 2022-2023 LG Electronics, Inc.

SUMMARY = "Status Bar Application"
AUTHOR = "Muniraju A<muniraju.a@lge.com>"
SECTION = "webos/apps"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://LICENSE;md5=86d3f3a95c324c9479bd8986968f4327 \
    file://oss-pkg-info.yaml;md5=0ec407cd2d4a192e0c60888f4ec66dd7 \
"

WEBOS_VERSION = "0.0.1-6_391a7829c540f4b41d3f34f478d37384a7585fc9"
SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"
PR = "r0"

inherit webos_enhanced_submissions
inherit webos_enactjs_app
inherit webos_public_repo


WEBOS_ENACTJS_APP_ID = "com.webos.app.statusbar"

# Workaround for network access issue during do_compile task
# http://gecko.lge.com/Errors/Details/447640
do_compile[network] = "1"