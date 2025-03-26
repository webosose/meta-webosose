# Copyright (c) 2020-2025 LG Electronics, Inc.

SUMMARY = "webOS extension for qtbase plugins"
AUTHOR = "Elvis Lee <kwangwoong.lee@lge.com>"
SECTION = "webos/base"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://LICENSE;md5=89aea4e17d99a7cacdbeed46a0096b10 \
    file://oss-pkg-info.yaml;md5=3f3152217d6f7d50567ddadebe5f22a2 \
"

DEPENDS = "qtbase"

WEBOS_VERSION = "1.0.0-33_82de55b24bdab921b0a41bb08631cea7e881e376"
PR = "r11"

inherit webos_qmake6
inherit webos_enhanced_submissions
inherit webos_public_repo
inherit webos_machine_impl_dep
inherit webos_qt_global
inherit features_check

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"

S = "${WORKDIR}/git"

REQUIRED_COMBINED_FEATURES = "webos-graphics-drm"

EXTRA_QMAKEVARS_PRE += "${PACKAGECONFIG_CONFARGS}"
PACKAGECONFIG ??= ""

# Emulator
PACKAGECONFIG[emulator] = "CONFIG+=emulator,,nyx-lib"
PACKAGECONFIG:append:emulator = " emulator"

# Multi-plane composition
PACKAGECONFIG[plane-composition] = "CONFIG+=plane_composition,,"

# EGL Protected content
PACKAGECONFIG[egl-protected-content] = "CONFIG+=egl_protected_content,,"

FILES:${PN} += " \
    ${OE_QMAKE_PATH_PLUGINS}/ \
"

# Set USE_X11/EGL_NO_X11 explicitly for using some eglplatform header.
# http://gecko.lge.com:8000/Errors/Details/750671
TARGET_CFLAGS:append = " ${@bb.utils.contains('DISTRO_FEATURES', 'x11', '-DUSE_X11', '-DEGL_NO_X11', d)}"
