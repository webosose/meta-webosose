# Copyright (c) 2013-2020 LG Electronics, Inc.

SUMMARY = "luna-surfacemanager qtwayland "
AUTHOR = "Anupam Kaul <anupam.kaul@lge.com>"

DEPENDS_append_class-target = " webos-wayland-extensions qt-features-webos"

# Build for raspberrypi4
# Mesa 19.1.1 uses xorgproto instead of xproto series.
DEPENDS_remove_raspberrypi4 = " xproto"
DEPENDS_append_raspberrypi4 = " xorgproto"

WEBOS_VERSION = "5.12.4-162_84428b39d3e82bc8fff79b3691bb6b237619aaa8"
EXTENDPRAUTO_append = "webos20"

LIC_FILES_CHKSUM = " \
    file://LICENSE.LGPL3;md5=e6a600fd5e1d9cbde2d983680233ad02 \
    file://LICENSE.GPL2;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
    file://LICENSE.GPL3;md5=d32239bcb673463ab874e80d47fae504 \
    file://LICENSE.GPL3-EXCEPT;md5=763d8c535a234d9a3fb682c7ecb6c073 \
    file://LICENSE.FDL;md5=6d9f2a9af4c8b8c3c769f6cc1b6aaf7e \
    file://oss-pkg-info.yaml;md5=ae1ac1ddbf8c4ad76d038d8f811750dc \
"

inherit webos_enhanced_submissions
inherit webos_qmake5
inherit webos_lttng

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

# our old version doesn't support extra parameters from PACKAGECONFIGs like:
# -no-feature-drm-egl-server -no-feature-libhybris-egl-server -no-feature-wayland-brcm -feature-wayland-client -feature-wayland-egl -feature-wayland-server -no-feature-xcomposite-egl -no-feature-xcomposite-glx
EXTRA_QMAKEVARS_CONFIGURE = ""

# And add CONFIG and DEFINES to EXTRA_QMAKEVARS_PRE
# because new recipe in meta-qt5 is using PACKAGECONFIG (and EXTRA_QMAKEVARS_CONFIGURE)
# instead of old QT_WAYLAND_CONFIG and QT_WAYLAND_DEFINES used by meta-webos qtwayland bbappend
EXTRA_QMAKEVARS_PRE += "CONFIG+=wayland-compositor CONFIG+=wayland_egl"
EXTRA_QMAKEVARS_PRE += "DEFINES+=QT_COMPOSITOR_QUICK"

# qtwayland-{plugins,qmlplugins} are not used in webOS
RRECOMMENDS_${PN}_remove = "${PN}-plugins ${PN}-qmlplugins"
