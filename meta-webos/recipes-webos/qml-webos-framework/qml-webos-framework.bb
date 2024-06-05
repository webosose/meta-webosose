# Copyright (c) 2014-2024 LG Electronics, Inc.

SUMMARY = "QML widgets and runtime framework for webOS apps"
AUTHOR = "Elvis Lee <kwangwoong.lee@lge.com>"
SECTION = "webos/base"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://LICENSE;md5=89aea4e17d99a7cacdbeed46a0096b10 \
    file://oss-pkg-info.yaml;md5=9e100013a76448cbe7c14134b0974453 \
"

DEPENDS = "qt-features-webos qtdeclarative qtwayland-webos pmloglib luna-service2 qttools-native"
DEPENDS:append = " ${@ 'qtshadertools-native' if d.getVar('QT_VERSION')[0] == '6' else '' }"
RDEPENDS:${PN} = "qtgraphicaleffects-qmlplugins"

RPROVIDES:${PN}-examples = " \
    eos.bare \
    eos.widgetgallery \
"

WEBOS_VERSION = "1.0.0-169_5a2860ca78815cb135dc4120f204c31bad5d7ab9"
PR = "r37"

inherit webos_qmake6
inherit webos_pkgconfig
inherit webos_enhanced_submissions
inherit webos_machine_dep
inherit webos_app_generate_security_files
inherit webos_filesystem_paths
inherit webos_distro_variant_dep
inherit webos_qmllint
inherit webos_public_repo
inherit features_check
ANY_OF_DISTRO_FEATURES = "vulkan opengl"

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

OE_QMAKE_PATH_HEADERS = "${OE_QMAKE_PATH_QT_HEADERS}"

# Perform extra QML validation
WEBOS_QMLLINT_EXTRA_VALIDATION = "1"

FILES:${PN} += "${OE_QMAKE_PATH_QML}/Eos/*"

PACKAGES += "${PN}-examples"
FILES:${PN}-examples += " \
    ${webos_applicationsdir}/* \
    ${datadir}/qml/locales/${BPN}/ \
"

# unit-tests
PACKAGES =+ "${PN}-tests"
FILES:${PN}-tests += "${datadir}/booster/tests/*"

# SDK tools
PACKAGES += "${PN}-tools"
FILES:${PN}-tools += "${webos_sdkdir}/*"

# we don't provide cmake tests
EXTRA_QMAKEVARS_POST += "CONFIG-=create_cmake"

# PACKAGECONFIG for smack feature
PACKAGECONFIG:append = " ${@bb.utils.filter('DISTRO_FEATURES', 'smack', d)}"
PACKAGECONFIG[smack] = "CONFIG+=enable_webos_smack"
EXTRA_QMAKEVARS_PRE += "${PACKAGECONFIG_CONFARGS}"
