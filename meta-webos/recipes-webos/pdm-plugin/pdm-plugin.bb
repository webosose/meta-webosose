# Copyright (c) 2019-2020 LG Electronics, Inc.

SUMMARY = " Pdm-plugin to support Physical device manager for webOS OSE"
DESCRIPTION = "Pdm-plugin to initialize hardware required by Physical device manager in for webOS OSE"
SECTION = "webos/services"
AUTHOR = "Preetham Bhat <preetham.bhat@lge.com>"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10 \
                    file://oss-pkg-info.yaml;md5=2bdfe040dcf81b4038370ae96036c519 \
"

DEPENDS = "com.webos.service.pdm"

WEBOS_VERSION = "1.0.1-9_3ce426d1080fd9db9f2bc62ecc6c2ae3b6da5ff9"
PR = "r2"

inherit webos_component
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_library
inherit webos_public_repo

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"
