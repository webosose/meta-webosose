# Copyright (c) 2013-2024 LG Electronics, Inc.

SUMMARY = "The core of the Luna Surface Manager (compositor)"
AUTHOR = "Elvis Lee <kwangwoong.lee@lge.com>"
SECTION = "webos/libs"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://LICENSE;md5=89aea4e17d99a7cacdbeed46a0096b10 \
    file://oss-pkg-info.yaml;md5=2c7c706c6a586a6abec428c64006d86b \
"

DEPENDS = "qtdeclarative wayland-native qtwayland qtwayland-native qt-features-webos pmloglib webos-wayland-extensions glib-2.0 qtwayland-webos"

WEBOS_VERSION = "2.0.0-418_32f9662865f96bef676e21114aad7d3b1690657f"
PR = "r64"

inherit webos_qmake6
inherit webos_pkgconfig
inherit webos_enhanced_submissions
inherit webos_lttng
inherit webos_public_repo
inherit features_check
ANY_OF_DISTRO_FEATURES = "vulkan opengl"

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"

S = "${WORKDIR}/git"

inherit webos_systemd
WEBOS_SYSTEMD_SERVICE = "lsm-ready.path lsm-ready.service lsm-ready.target surface-manager.service surface-manager-daemon.service"

OE_QMAKE_PATH_HEADERS = "${OE_QMAKE_PATH_QT_HEADERS}"

# Enable LTTng tracing capability when enabled in webos_lttng class
EXTRA_QMAKEVARS_PRE += "${@ 'CONFIG+=lttng' if '${WEBOS_LTTNG_ENABLED}' == '1' else '' }"

# DRM/KMS configurations
EGLFS_INTEGRATION ?= "eglfs_kms_webos"
DRM_FORMAT ?= "abgr8888"
EXTRA_QMAKEVARS_PRE += "${@bb.utils.contains('COMBINED_FEATURES', 'webos-graphics-drm', 'CONFIG+=backend_drm EGLFS_INTEGRATION=${EGLFS_INTEGRATION} DRM_FORMAT=${DRM_FORMAT}', '', d)}"
EXTRA_QMAKEVARS_PRE:append:qemuall = " CONFIG+=virtual_display_support"

EXTRA_QMAKEVARS_PRE += "${PACKAGECONFIG_CONFARGS}"

# We don't support configuring via cmake
EXTRA_QMAKEVARS_POST += "CONFIG-=create_cmake"

FILES:${PN}-dev += " \
    ${OE_QMAKE_PATH_QT_ARCHDATA}/mkspecs/* \
    ${OE_QMAKE_PATH_LIBS}/*.prl \
"

do_install:append() {
    sed -i 's@prefix=${STAGING_DIR_HOST}@prefix=@g;s@-L${STAGING_DIR_HOST} @ @g;' ${D}${libdir}/pkgconfig/*.pc
    sed -i "s@-L${STAGING_LIBDIR}@-L\${libdir}@g" ${D}${libdir}/pkgconfig/*.pc
    if ${@bb.utils.contains('PACKAGECONFIG', 'compositor', 'true', 'false', d)}; then
        install -d ${D}${datadir}/webos-keymap
        ${STAGING_DIR_NATIVE}${OE_QMAKE_PATH_QT_BINS}/generate_qmap ${D}${datadir}/webos-keymap/webos-keymap.qmap
    fi

    if ${@bb.utils.contains('IMAGE_FEATURES', 'webos-test', 'true', 'false', d)}; then
        mkdir -p ${D}${libdir}/${BPN}
        find ${B} -name \*.gcno -exec cp -t ${D}${libdir}/${BPN} {} \;
    fi
}

TARGET_CXXFLAGS:append = " ${@bb.utils.contains('IMAGE_FEATURES', 'webos-test', '--coverage -fprofile-dir=/tmp/luna-surfacemanager-gcov -O0', '', d)}"
TARGET_LDFLAGS:append = " ${@bb.utils.contains('IMAGE_FEATURES', 'webos-test', '--coverage', '', d)}"

VIRTUAL-RUNTIME_gpu-libs ?= ""
RDEPENDS:${PN} += "${VIRTUAL-RUNTIME_gpu-libs}"

# Select platform abstraction plugin
VIRTUAL-RUNTIME_lsm-qpa ?= ""
RDEPENDS:${PN} += "${VIRTUAL-RUNTIME_lsm-qpa}"

inherit webos_system_bus
inherit webos_qmllint

# Make sure to contain com.webos.surfacemanager service's files only
WEBOS_SYSTEM_BUS_MANIFEST_TYPE = "SERVICE"

# qt-features-webos have its own logic to install system bus files reason for
# that is because only qmake knows where substitued files will be placed.
WEBOS_SYSTEM_BUS_SKIP_DO_TASKS = "1"

# Perform extra QML validation
WEBOS_QMLLINT_EXTRA_VALIDATION = "1"

PACKAGECONFIG[compositor] = "CONFIG+=compositor_base,,qt-features-webos-native"
PACKAGECONFIG[multi-input] = ",CONFIG+=no_multi_input,"
PACKAGECONFIG[cursor-theme] = "CONFIG+=cursor_theme,,"

PACKAGECONFIG = "compositor cursor-theme"

PACKAGE_BEFORE_PN = "${PN}-gcov"

FILES:${PN}-gcov = " \
    ${libdir}/${BPN}/*.gcno \
"

PACKAGES =+ "${PN}-conf ${PN}-base ${PN}-base-tests"

FILES:${PN}-conf += " \
    ${webos_sysbus_apipermissionsdir} \
    ${webos_sysbus_groupsdir} \
    ${webos_sysbus_servicedir} \
    ${webos_sysbus_manifestsdir}/com.webos.surfacemanager.manifest.json \
    ${webos_sysbus_permissionsdir}/com.webos.surfacemanager.perm.json \
    ${webos_sysbus_rolesdir}/com.webos.surfacemanager.role.json \
"

FILES:${PN}-base += " \
    ${OE_QMAKE_PATH_QML}/WebOSCompositorBase/ \
    ${OE_QMAKE_PATH_QML}/WebOSCompositor/ \
    ${OE_QMAKE_PATH_BINS}/ \
    ${datadir}/icons/ \
    ${datadir}/webos-keymap/webos-keymap.qmap \
    ${sysconfdir}/surface-manager.d/ \
"

FILES:${PN}-base-tests += " \
    ${webos_applicationsdir}/ \
    ${webos_sysbus_manifestsdir}/ \
    ${webos_sysbus_permissionsdir}/ \
    ${webos_sysbus_rolesdir}/ \
    ${webos_testsdir}/${BPN}/ \
"

RDEPENDS:${PN}-base += "xkeyboard-config qml-webos-framework qml-webos-bridge qml-webos-components qtdeclarative-qmlplugins notificationmgr"
