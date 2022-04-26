# Copyright (c) 2019-2022 LG Electronics, Inc.

SUMMARY = "Timed Automata library for Event translation"
AUTHOR  = "Byunggul Koh <byunggul.koh@lge.com>"
SECTION = "webos/libs"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://LICENSE;md5=89aea4e17d99a7cacdbeed46a0096b10 \
    file://oss-pkg-info.yaml;md5=cb8a469702ce6c0681fd122a17c16af8 \
"

WEBOS_VERSION = "0.0.1-5_efbec9b440ad9e1201b26aae868310935c32e010"
PR = "r3"

inherit webos_enhanced_submissions
inherit webos_public_repo
inherit pkgconfig cmake

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"
