# Copyright (c) 2013-2018 LG Electronics, Inc.

DESCRIPTION = "System Application Manager"
AUTHOR = "Sangwoo Kang <sangwoo82.kang@lge.com>"
SECTION = "webos/base"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

DEPENDS = "gtest glib-2.0 luna-service2 libpbnjson boost icu pmloglib librolegen procps libwebosi18n"
RDEPENDS_${PN} = "ecryptfs-utils"
RDEPENDS_${PN} += "${VIRTUAL-RUNTIME_webos-customization}"

VIRTUAL-RUNTIME_webos-customization ?= ""

WEBOS_VERSION = "2.0.0-2_eb57cb7e68f4f028e23f2369a4a0972efa8c38b8"
PR = "r17"

inherit webos_component
inherit webos_cmake
inherit webos_enhanced_submissions
inherit webos_daemon
inherit webos_system_bus
inherit webos_public_repo

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

PACKAGES =+ "${PN}-tests"
ALLOW_EMPTY_${PN}-tests = "1"
FILES_${PN}-tests = "${libexecdir}/tests/*"
