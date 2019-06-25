# Copyright (c) 2012-2019 LG Electronics, Inc.

SUMMARY = "libsandbox is a collection of APIs for separating running programs"
AUTHOR = "Andrew Innes <andrew.innes@lge.com>"
SECTION = "webos/devel"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

WEBOS_VERSION = "2.0.0-1_af7b86fde7acd9fd293fe01ee859fcc52fa8b433"
PR = "r2"

inherit webos_component
inherit webos_public_repo
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_library
inherit webos_machine_impl_dep

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

ALLOW_EMPTY_${PN} = "1"
