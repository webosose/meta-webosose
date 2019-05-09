# Copyright (c) 2016-2019 LG Electronics, Inc.

SUMMARY = "webOS portability layer - qemux86 nyx module"
AUTHOR  = "Sapna Todwal <sapna.todwal@lge.com>"
SECTION = "webos/base"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

DEPENDS = "nyx-lib glib-2.0 luna-service2 openssl udev"

inherit webos_component
inherit webos_public_repo
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_library
inherit webos_nyx_module_provider

WEBOS_VERSION = "1.0.0-2_3f3f8b96a48fd64622992748a5d9308f21ee5451"
PR = "r0"

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"
