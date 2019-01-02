# Copyright (c) 2018-2019 LG Electronics, Inc.

SUMMARY = "VAL API implementation library mock"
AUTHOR = "Kwanghee Lee <ekwang.lee@lge.com>"
SECTION = "webos/libs"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e3fc50a88d0a364313df4b21ef20c29e"

PROVIDES = "val-impl"

inherit webos_component
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_library
inherit webos_test_provider
inherit webos_public_repo

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

DEPENDS = "glib-2.0 ls2-helpers pmloglib libpbnjson videooutput-adaptation-layer-api"

WEBOS_VERSION = "1.0.0-2_176a7cc7010bcf8bbcb9f2449db16f95e71cfe45"
PR = "r0"

