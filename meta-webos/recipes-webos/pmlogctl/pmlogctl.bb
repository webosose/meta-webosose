# Copyright (c) 2012-2024 LG Electronics, Inc.

SUMMARY = "webOS logging control application"
AUTHOR = "Sukil Hong <sukil.hong@lge.com>"
SECTION = "webos/base"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10 \
    file://oss-pkg-info.yaml;md5=2bdfe040dcf81b4038370ae96036c519 \
"

DEPENDS = "pmloglib"

WEBOS_VERSION = "3.0.0-4_20c4da363c32f62d8c904ac413ed95e9dc34f4a2"
PR = "r4"

inherit webos_component
inherit webos_public_repo
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_program

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"
