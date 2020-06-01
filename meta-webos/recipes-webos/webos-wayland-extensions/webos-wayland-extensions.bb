# Copyright (c) 2013-2020 LG Electronics, Inc.

SUMMARY = "Wayland protocol extensions for webOS"
AUTHOR = "Anupam Kaul <anupam.kaul@lge.com>"
SECTION = "webos/base"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10 \
    file://oss-pkg-info.yaml;md5=37b39c3b46a59a4e2436b172ca90faaf \
"

DEPENDS = "wayland wayland-native"

WEBOS_VERSION = "1.0.0-38_3f477ba14c96093cb36e4a1cd7c654f38dae032f"
PR = "r3"

inherit webos_component
inherit webos_cmake
inherit webos_enhanced_submissions
inherit webos_public_repo

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

FILES_${PN}-dev += "${datadir}/*"
