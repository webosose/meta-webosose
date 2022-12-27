# Copyright (c) 2017-2023 LG Electronics, Inc.

SUMMARY = "webOS connman adapter support API"
AUTHOR = "Muralidhar N <muralidhar.n@lge.com>"
SECTION = "webos/libs"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10 \
    file://oss-pkg-info.yaml;md5=6846cc431bc5281393713ebd4300401d \
"

DEPENDS = "libpbnjson luna-service2"

WEBOS_VERSION = "1.0.0-4_54eca17251c81e7291893682ed86bc39a8f568ac"
PR = "r2"

inherit webos_component
inherit webos_enhanced_submissions
inherit webos_pkgconfig
inherit webos_cmake
inherit webos_public_repo

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"
