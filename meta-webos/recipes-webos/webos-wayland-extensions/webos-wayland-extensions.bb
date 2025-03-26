# Copyright (c) 2013-2025 LG Electronics, Inc.

SUMMARY = "Wayland protocol extensions for webOS"
AUTHOR = "Elvis Lee <kwangwoong.lee@lge.com>"
SECTION = "webos/base"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://LICENSE;md5=89aea4e17d99a7cacdbeed46a0096b10 \
    file://oss-pkg-info.yaml;md5=3b9914d0b76f24145c4c707c66c944bc \
"

DEPENDS = "wayland wayland-native"

WEBOS_VERSION = "1.0.0-48_07b0ddfa4f72d7eeff80c8263e80a17f5f836156"
PR = "r7"

inherit webos_component
inherit webos_cmake
inherit webos_pkgconfig
inherit webos_enhanced_submissions
inherit webos_public_repo

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

FILES:${PN}-dev += "${datadir}/*"
