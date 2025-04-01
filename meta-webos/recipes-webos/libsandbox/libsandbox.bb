# Copyright (c) 2012-2025 LG Electronics, Inc.

SUMMARY = "libsandbox is a collection of APIs for separating running programs"
AUTHOR = "Vibhanshu Dhote <vibhanshu.dhote@lge.com>"
SECTION = "webos/devel"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10 \
    file://oss-pkg-info.yaml;md5=629222c36a9c68f9e1855667faef01ae \
"

WEBOS_VERSION = "2.0.0-3_03123aaa6b341f7cb838ace978223a5617f2555c"
PR = "r3"

inherit webos_component
inherit webos_public_repo
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_library
inherit webos_machine_impl_dep

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

ALLOW_EMPTY:${PN} = "1"
