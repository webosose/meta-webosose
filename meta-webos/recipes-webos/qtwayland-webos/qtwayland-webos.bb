# Copyright (c) 2016-2019 LG Electronics, Inc.

SUMMARY = "webOS extension for Qtwayland"
AUTHOR = "Elvis Lee <kwangwoong.lee@lge.com>"
SECTION = "webos/base"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

DEPENDS = "qtwayland webos-wayland-extensions libxkbcommon qt-features-webos wayland-native qtwayland-native"

WEBOS_VERSION = "1.0.0-43_dadbc3c9d618c05e9fff84f181918546d158308b"
PR = "r8"

inherit webos_qmake5
inherit webos_enhanced_submissions
inherit webos_lttng
inherit webos_public_repo

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

# No debian package renaming
DEBIAN_NOAUTONAME_${PN} = "1"
DEBIAN_NOAUTONAME_${PN}-dbg = "1"
DEBIAN_NOAUTONAME_${PN}-dev = "1"

# Enable LTTng tracing capability when enabled in webos_lttng class
EXTRA_QMAKEVARS_PRE += "${@oe.utils.conditional('WEBOS_LTTNG_ENABLED', '1', 'CONFIG+=lttng', '', d)}"

# we don't provide cmake tests
EXTRA_QMAKEVARS_POST += "CONFIG-=create_cmake"

PACKAGECONFIG ??= ""
PACKAGECONFIG[criu] = "CONFIG+=criu,,criu-webos"

EXTRA_QMAKEVARS_PRE += "${PACKAGECONFIG_CONFARGS}"

FILES_${PN} += " \
    ${OE_QMAKE_PATH_PLUGINS}/*/*${SOLIBSDEV} \
"

# FIXME: weboscompositorextensionclient is deprecated and merged into
# webos-platform-interface. We provide weboscompositorextensionclient.pc
# as an alias of webos-platform-interface.pc so that components used to
# depend on weboscompositorextensionclient build and run as before.
# This should be removed once all these components have no dependency
# on weboscompositorextensionclient.
do_install_append() {
    ln -snvf webos-platform-interface.pc ${D}${libdir}/pkgconfig/weboscompositorextensionclient.pc
    sed -i 's@prefix=${STAGING_DIR_HOST}@prefix=@g ;s@-L${STAGING_DIR_HOST} @ @g;' ${D}${libdir}/pkgconfig/*.pc
    sed -i "s@-L${STAGING_LIBDIR}@-L\${libdir}@g" ${D}${libdir}/pkgconfig/*.pc
}

SRC_URI += "file://0001-webos-wayland-egl.pro-link-with-wayland-cursor.patch"
