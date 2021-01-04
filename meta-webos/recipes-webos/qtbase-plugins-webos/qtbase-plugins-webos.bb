# Copyright (c) 2020-2021 LG Electronics, Inc.

SUMMARY = "webOS extension for qtbase plugins"
AUTHOR = "Elvis Lee <kwangwoong.lee@lge.com>"
SECTION = "webos/base"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10 \
    file://oss-pkg-info.yaml;md5=3f3152217d6f7d50567ddadebe5f22a2 \
"

DEPENDS = "qtbase"

WEBOS_VERSION = "1.0.0-2_9f5c6ab2367c8f68a1e3a53df9849d7a02880006"
PR = "r0"

inherit webos_qmake5
inherit webos_enhanced_submissions
inherit webos_public_repo
inherit webos_machine_impl_dep

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

EXTRA_QMAKEVARS_PRE += "${PACKAGECONFIG_CONFARGS}"
PACKAGECONFIG ??= ""

# Emulator
PACKAGECONFIG[emulator] = "CONFIG+=emulator,,nyx-lib"
PACKAGECONFIG_append_emulator = " emulator"

# Multi-plane composition
PACKAGECONFIG[plane-composition] = "CONFIG+=plane_composition,,"

FILES_${PN} += " \
    ${OE_QMAKE_PATH_PLUGINS}/ \
"
