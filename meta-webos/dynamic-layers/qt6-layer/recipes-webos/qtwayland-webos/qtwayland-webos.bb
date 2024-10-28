# Copyright (c) 2016-2024 LG Electronics, Inc.

SUMMARY = "webOS extension for Qtwayland"
AUTHOR = "Elvis Lee <kwangwoong.lee@lge.com>"
SECTION = "webos/base"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://LICENSE;md5=89aea4e17d99a7cacdbeed46a0096b10 \
    file://oss-pkg-info.yaml;md5=7187b1fb0318bb1af23edbf4237ee8b8 \
"

DEPENDS = "qtwayland webos-wayland-extensions libxkbcommon qt-features-webos wayland-native qtwayland-native wayland-protocols"

WEBOS_VERSION = "6.0.0-93_fa22224e6e6549d89c19f99a984a63c3062dd4f5"
PR = "r21"

QT_BUILD_SYSTEM ?= "${@ 'cmake' if d.getVar('QT_VERSION')[0] == '6' else 'qmake' }"

PACKAGECONFIG ??= ""

# qtwayland-webos_cmake.inc or qtwayland-webos_qmake.inc
require ${BPN}_${QT_BUILD_SYSTEM}.inc

inherit webos_pkgconfig
inherit webos_enhanced_submissions
inherit webos_lttng
inherit webos_public_repo
inherit features_check
ANY_OF_DISTRO_FEATURES = "vulkan opengl"

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"

S = "${WORKDIR}/git"

# Enable LTTng tracing capability when enabled in webos_lttng class
PACKAGECONFIG:append = "${@ ' lttng' if '${WEBOS_LTTNG_ENABLED}' == '1' else ''}"

# No debian package renaming
DEBIAN_NOAUTONAME:${PN} = "1"
DEBIAN_NOAUTONAME:${PN}-dbg = "1"
DEBIAN_NOAUTONAME:${PN}-dev = "1"

FILES:${PN} += " \
    ${libdir}/plugins/*/*${SOLIBSDEV} \
"

FILES:${PN}-dev += " \
    ${libdir}/*.prl \
    ${libdir}/mkspecs/* \
"

# FIXME: weboscompositorextensionclient is deprecated and merged into
# webos-platform-interface. We provide weboscompositorextensionclient.pc
# as an alias of webos-platform-interface.pc so that components used to
# depend on weboscompositorextensionclient build and run as before.
# This should be removed once all these components have no dependency
# on weboscompositorextensionclient.
do_install:append() {
    ln -snvf webos-platform-interface.pc ${D}${libdir}/pkgconfig/weboscompositorextensionclient.pc
    sed -i 's@prefix=${STAGING_DIR_HOST}@prefix=@g ;s@-L${STAGING_DIR_HOST} @ @g;' ${D}${libdir}/pkgconfig/*.pc
    sed -i "s@-L${STAGING_LIBDIR}@-L\${libdir}@g" ${D}${libdir}/pkgconfig/*.pc
}
