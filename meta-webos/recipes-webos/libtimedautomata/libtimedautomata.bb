# Copyright (c) 2019-2020 LG Electronics, Inc.

SUMMARY = "Timed Automata library for Event translation"
AUTHOR  = "Byunggul Koh <byunggul.koh@lge.com>"
SECTION = "webos/libs"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

WEBOS_VERSION = "0.0.1-3_d7071b49d5d2ee72dcc87cff4689b49b7d71c502"
PR = "r0"

inherit webos_enhanced_submissions
inherit webos_public_repo
inherit pkgconfig cmake

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"
