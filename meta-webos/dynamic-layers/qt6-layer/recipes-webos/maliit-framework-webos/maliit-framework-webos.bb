# Copyright (c) 2013-2025 LG Electronics, Inc.

SUMMARY = "Maliit Input Method Framework"
DESCRIPTION = "This is the webOS edition of the Maliit input method framework. It differs from upstream in that it supports a hardware keyboard using the wayland protocol."
AUTHOR = "Elvis Lee <kwangwoong.lee@lge.com>"
SECTION = "webos/base"
LICENSE = "LGPL-2.0-only"
LIC_FILES_CHKSUM = " \
    file://LICENSE.LGPL;md5=5c917f6ce94ceb8d8d5e16e2fca5b9ad \
    file://oss-pkg-info.yaml;md5=1b633545a82af651ad37b9f03288651e \
"

DEPENDS = "qtbase qtdeclarative qtwayland-webos libxkbcommon pmloglib luna-service2 glib-2.0 udev wayland"
RDEPENDS:${PN} = "qtbase-plugins configd"

PACKAGECONFIG[libim] = "CONFIG+=enable-libim,CONFIG-=enable-libim,libim"

WEBOS_VERSION = "0.99.0+20-102_b3c5fe41a33b6dd3d5c11b704c6ff2c8974ef7b6"
PR = "r37"

inherit webos_daemon
inherit webos_enhanced_submissions
inherit webos_qmake6
inherit webos_machine_impl_dep
inherit webos_filesystem_paths
inherit webos_public_repo
inherit features_check
ANY_OF_DISTRO_FEATURES = "vulkan opengl"

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

inherit webos_systemd
WEBOS_SYSTEMD_SERVICE = "maliit-server@.service maliit-server.service"
WEBOS_SYSTEMD_SCRIPT = "maliit-server.sh"

OE_QMAKE_PATH_HEADERS = "${OE_QMAKE_PATH_QT_HEADERS}"

EXTRA_QMAKEVARS_PRE += "CONFIG+=wayland MALIIT_DEFAULT_PLUGIN=libplugin-global.so CONFIG+=noxcb CONFIG+=nodoc CONFIG+=notests CONFIG+=noexamples"
EXTRA_QMAKEVARS_PRE += "INCDIR=${STAGING_INCDIR} INCLUDEDIR=${STAGING_INCDIR} LIBDIR=${STAGING_LIBDIR} MALIIT_PLUGINS_DIR=${libdir}/maliit/plugins MALIIT_DATA_DIR=${webos_execstatedir}/maliit"
EXTRA_QMAKEVARS_PRE += "MALIIT_VERSION=${PV}"
EXTRA_QMAKEVARS_PRE += "WEBOS_TARGET_MACHINE_IMPL=${WEBOS_TARGET_MACHINE_IMPL}"
EXTRA_QMAKEVARS_PRE += "${EXTRA_CONF_PACKAGECONFIG}"

# .pc generation should be fixed to use correct paths
SSTATE_SCAN_FILES += "*.prf *.pc"

do_install:append() {
    # headers
    install -d ${D}${includedir}/maliit
    install -v -m 644 ${S}/common/maliit/*.h ${D}${includedir}/maliit/
    install -d ${D}${includedir}/maliit/plugins
    install -v -m 644 ${S}/src/maliit/plugins/*.h ${D}${includedir}/maliit/plugins/
}

FILES:${PN}-dev += "${OE_QMAKE_PATH_QT_ARCHDATA}/mkspecs"

do_install:append() {
    sed -i 's@libdir=${STAGING_LIBDIR}@libdir=${libdir}@g' ${D}${libdir}/pkgconfig/*.pc
    sed -i 's@includedir=${STAGING_INCDIR}@includedir=${includedir}@g' ${D}${libdir}/pkgconfig/*.pc
}

FILES:${PN} += "${OE_QMAKE_PATH_QT_ARCHDATA}"

# FIXME-buildpaths!!!
# [WRP-10883] buildpath QA issues
# http://gecko.lge.com:8000/Errors/Details/894427
# ERROR: QA Issue: File /usr/lib/mkspecs/features/maliit-plugins.prf in package maliit-framework-webos-dev contains reference to TMPDIR
# File /usr/lib/mkspecs/features/maliit-defines.prf in package maliit-framework-webos-dev contains reference to TMPDIR
# File /usr/lib/mkspecs/features/maliit-framework.prf in package maliit-framework-webos-dev contains reference to TMPDIR [buildpaths]
ERROR_QA:remove = "buildpaths"
WARN_QA:append = " buildpaths"
