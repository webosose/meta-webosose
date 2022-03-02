# Copyright (c) 2013-2022 LG Electronics, Inc.

SUMMARY = "Wayland protocol extensions for webOS"
AUTHOR = "Anupam Kaul <anupam.kaul@lge.com>"
SECTION = "webos/base"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://LICENSE;md5=89aea4e17d99a7cacdbeed46a0096b10 \
    file://oss-pkg-info.yaml;md5=3b9914d0b76f24145c4c707c66c944bc \
"

DEPENDS = "wayland wayland-native"

WEBOS_VERSION = "1.0.0-43_a0fe7d92b4d6c935f626ccd64087b372850b5d65"
PR = "r6"

inherit webos_component
inherit webos_cmake
inherit webos_pkgconfig
inherit webos_enhanced_submissions
inherit webos_public_repo

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

FILES_${PN}-dev += "${datadir}/*"
