# Copyright (c) 2013-2019 LG Electronics, Inc.

SUMMARY = "luna-surfacemanager qtwayland "
AUTHOR = "Anupam Kaul <anupam.kaul@lge.com>"

DEPENDS_append_class-target = " webos-wayland-extensions qt-features-webos"

WEBOS_VERSION = "5.6.3-4_21bcc61d4a3da2873d9650b19771ffddf31d6bc0"
EXTENDPRAUTO_append = "webos15"

# Upstream 5.5.0 recipe updated LIC_FILES_CHKSUM
LIC_FILES_CHKSUM = " \
    file://LICENSE.LGPLv21;md5=4bfd28363f541b10d9f024181b8df516 \
    file://LICENSE.LGPLv3;md5=e0459b45c5c4840b353141a8bbed91f0 \
    file://LICENSE.GPLv3;md5=88e2b9117e6be406b5ed6ee4ca99a705 \
    file://LGPL_EXCEPTION.txt;md5=9625233da42f9e0ce9d63651a9d97654 \
    file://LICENSE.FDL;md5=6d9f2a9af4c8b8c3c769f6cc1b6aaf7e \
"

inherit webos_enhanced_submissions
inherit webos_qmake5
inherit webos_lttng

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

# Assume we're using the same Qt version to this component has.
QT_VERSION = "${WEBOS_COMPONENT_VERSION}"

inherit webos_public_repo

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

# By default the recipe will not compile any specific backend for the compositor.
QT_WAYLAND_CONFIG_append = " CONFIG+=wayland_egl"
QT_WAYLAND_DEFINES_append = "QT_COMPOSITOR_QUICK"

# Enable LTTng tracing capability when enabled in webos_lttng class
EXTRA_QMAKEVARS_PRE += "${@oe.utils.conditional('WEBOS_LTTNG_ENABLED', '1', 'CONFIG+=lttng', '', d)}"

EXTRA_QMAKEVARS_POST += "CONFIG-=create_cmake"

do_install_append_class-target() {
    sed -i \
        -e 's/fontdatabase_support//g' \
        -e 's/eventdispatcher_support//g' \
        -e 's/theme_support//g' \
        -e 's/egl_support//g' \
        ${D}${OE_QMAKE_PATH_QT_ARCHDATA}/mkspecs/modules/qt_lib_waylandclient.pri \
        || bbwarn "${D}${OE_QMAKE_PATH_QT_ARCHDATA}/mkspecs/modules/qt_lib_waylandclient.pri is missing or the sed call failed"
}
