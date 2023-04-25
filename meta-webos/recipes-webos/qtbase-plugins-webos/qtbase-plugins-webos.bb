# Copyright (c) 2020-2023 LG Electronics, Inc.

SUMMARY = "webOS extension for qtbase plugins"
AUTHOR = "Elvis Lee <kwangwoong.lee@lge.com>"
SECTION = "webos/base"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://LICENSE;md5=89aea4e17d99a7cacdbeed46a0096b10 \
    file://oss-pkg-info.yaml;md5=3f3152217d6f7d50567ddadebe5f22a2 \
"

DEPENDS = "qtbase"

WEBOS_VERSION = "1.0.0-14_c4b4a838abb44892c6a348365f9f6b81ed5b9376"
PR = "r5"

inherit webos_qmake6
inherit webos_enhanced_submissions
inherit webos_public_repo
inherit webos_machine_impl_dep
inherit webos_qt_global

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE} \
    file://0001-fix-build-with-64-bit-time_t.patch \
"
S = "${WORKDIR}/git"

EXTRA_QMAKEVARS_PRE += "${PACKAGECONFIG_CONFARGS}"
PACKAGECONFIG ??= ""

# Emulator
PACKAGECONFIG[emulator] = "CONFIG+=emulator,,nyx-lib"
PACKAGECONFIG:append:emulator = " emulator"
SRC_URI:append:emulator = " \
    file://0001-virtual-touch-for-emulator.patch \
    file://0002-Disable-mouse-wheel-on-touch-mode-for-emulator.patch \
"

# Multi-plane composition
PACKAGECONFIG[plane-composition] = "CONFIG+=plane_composition,,"

# EGL Protected content
PACKAGECONFIG[egl-protected-content] = "CONFIG+=egl_protected_content,,"

FILES:${PN} += " \
    ${OE_QMAKE_PATH_PLUGINS}/ \
"
