# Copyright (c) 2021-2024 LG Electronics, Inc.

SUMMARY = "Video Player application"
AUTHOR = "VINH VAN LE <vinh5.le@lge.com>"
SECTION = "webos/apps"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://LICENSE;md5=86d3f3a95c324c9479bd8986968f4327 \
    file://oss-pkg-info.yaml;md5=3072ffcf5bdbbc376ed21c9d378d14d5 \
"

WEBOS_VERSION = "0.0.1-12_ff6e0081d1b0f8ad1bf0480c5f28b2616cd646d4"
PR = "r3"

inherit webos_component
inherit webos_enhanced_submissions
inherit webos_enactjs_app
inherit webos_public_repo

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

WEBOS_ENACTJS_APP_ID = "com.webos.app.videoplayer"
WEBOS_LOCALIZATION_DATA_PATH = "${S}"
WEBOS_LOCALIZATION_XLIFF_BASENAME = "videoplayer"

# Workaround for network access issue during do_compile task
do_compile[network] = "1"
