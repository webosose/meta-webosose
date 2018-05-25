# Copyright (c) 2017-2018 LG Electronics, Inc.

SUMMARY = "AV API implementation library mock"
AUTHOR = "Soumya Aithal <soumya.aithal@lge.com>"
SECTION = "webos/libs"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

PROVIDES = "aval-impl"

inherit webos_component
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_library
inherit webos_test_provider
inherit webos_public_repo

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

DEPENDS = "glib-2.0 pmloglib libpbnjson ls2-helpers avoutput-adaptation-layer-api"

WEBOS_VERSION = "1.0.0-1_14419deaaa797c7c451047df2f5878577319b12f"
PR = "r0"

