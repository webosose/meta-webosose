# Copyright (c) 2013-2020 LG Electronics, Inc.

SUMMARY = "Maliit Input Method Framework"
DESCRIPTION = "This is the webOS edition of the Maliit input method framework. It differs from upstream in that it supports a hardware keyboard using the wayland protocol."
AUTHOR = "Minjoong Park <minjoong.park@lgepartner.com>"
SECTION = "webos/base"
LICENSE = "LGPLv2"
LIC_FILES_CHKSUM = "file://LICENSE.LGPL;md5=5c917f6ce94ceb8d8d5e16e2fca5b9ad"

DEPENDS = "qtbase qtdeclarative qtwayland-webos libxkbcommon pmloglib luna-service2 glib-2.0 udev wayland"
RDEPENDS_${PN} = "qtbase-plugins configd"

PACKAGECONFIG[libim] = "CONFIG+=enable-libim,CONFIG-=enable-libim,libim"

WEBOS_VERSION = "0.99.0+20-92_daac88d56f042a84be91aab9ce8fd8a856ed7f49"
PR = "r31"

inherit webos_daemon
inherit webos_enhanced_submissions
inherit webos_qmake5
inherit webos_machine_impl_dep
inherit webos_filesystem_paths
inherit webos_public_repo

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

OE_QMAKE_PATH_HEADERS = "${OE_QMAKE_PATH_QT_HEADERS}"

EXTRA_QMAKEVARS_PRE += "CONFIG+=wayland MALIIT_DEFAULT_PLUGIN=libplugin-global.so CONFIG+=noxcb CONFIG+=nodoc CONFIG+=notests CONFIG+=noexamples"
EXTRA_QMAKEVARS_PRE += "INCDIR=${STAGING_INCDIR} INCLUDEDIR=${STAGING_INCDIR} LIBDIR=${STAGING_LIBDIR} MALIIT_PLUGINS_DIR=${libdir}/maliit/plugins MALIIT_DATA_DIR=${webos_execstatedir}/maliit"
EXTRA_QMAKEVARS_PRE += "MALIIT_VERSION=${PV}"
EXTRA_QMAKEVARS_PRE += "WEBOS_TARGET_MACHINE_IMPL=${WEBOS_TARGET_MACHINE_IMPL}"
EXTRA_QMAKEVARS_PRE += "${EXTRA_CONF_PACKAGECONFIG}"

# .pc generation should be fixed to use correct paths
SSTATE_SCAN_FILES += "*.prf *.pc"

do_install_append() {
    # headers
    install -d ${D}${includedir}/maliit
    install -v -m 644 ${S}/common/maliit/*.h ${D}${includedir}/maliit/
    install -d ${D}${includedir}/maliit/plugins
    install -v -m 644 ${S}/src/maliit/plugins/*.h ${D}${includedir}/maliit/plugins/
}

FILES_${PN}-dev += "${OE_QMAKE_PATH_QT_ARCHDATA}/mkspecs"

do_install_append() {
    sed -i 's@libdir=${STAGING_LIBDIR}@libdir=${libdir}@g' ${D}${libdir}/pkgconfig/*.pc
    sed -i 's@includedir=${STAGING_INCDIR}@includedir=${includedir}@g' ${D}${libdir}/pkgconfig/*.pc
}

FILES_${PN} += "${OE_QMAKE_PATH_QT_ARCHDATA}"
