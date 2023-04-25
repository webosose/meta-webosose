# Copyright (c) 2017-2023 LG Electronics, Inc.

SUMMARY = "Luna service C++11 helpers library"
AUTHOR = "Yogish S <yogish.s@lge.com>"
SECTION = "webos/services"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

DEPENDS = "glib-2.0 luna-service2 pmloglib libpbnjson"

WEBOS_VERSION = "1.0.0-1_d44eeede2de5b06d08fd86ace6e93f5aed9f7f27"
PR = "r2"

inherit webos_component
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_library
inherit webos_test_provider
inherit webos_public_repo

# [http://gpro.lge.com/c/webosose/ls2-helpers/+/348043 test_subscriptionpoint.cpp: Prevent issues with new gtest]                                                                                                    
# [http://gpro.lge.com/c/webosose/ls2-helpers/+/348042 Fix build with gcc-12]
SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE} \
    file://0001-test_subscriptionpoint.cpp-Prevent-issues-with-new-g.patch \
    file://0002-Fix-build-with-gcc-12.patch \
"
S = "${WORKDIR}/git"
