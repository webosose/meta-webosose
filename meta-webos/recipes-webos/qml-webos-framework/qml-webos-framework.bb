# Copyright (c) 2014-2021 LG Electronics, Inc.

SUMMARY = "QML widgets and runtime framework for webOS apps"
AUTHOR = "Mikko Levonmaa <mikko.levonmaa@lge.com>"
SECTION = "webos/base"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://LICENSE;md5=89aea4e17d99a7cacdbeed46a0096b10 \
    file://oss-pkg-info.yaml;md5=9e100013a76448cbe7c14134b0974453 \
"

DEPENDS = "qt-features-webos qtdeclarative qtwayland-webos pmloglib luna-service2"
DEPENDS_append = " ${@ 'qtshadertools-native' if d.getVar('QT_VERSION', True) == '6' else '' }"
RDEPENDS_${PN} = "qtgraphicaleffects-qmlplugins"

RPROVIDES_${PN}-examples = " \
    eos.bare \
    eos.widgetgallery \
"

WEBOS_VERSION = "1.0.0-160_d89ab0a01a224a6139bdfa916736df67ff6dda44"
PR = "r33"

inherit webos_qmake6
inherit webos_enhanced_submissions
inherit webos_machine_dep
inherit webos_app_generate_security_files
inherit webos_filesystem_paths
inherit webos_distro_variant_dep
inherit webos_qmllint
inherit webos_public_repo

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

OE_QMAKE_PATH_HEADERS = "${OE_QMAKE_PATH_QT_HEADERS}"

# Perform extra QML validation
WEBOS_QMLLINT_EXTRA_VALIDATION = "1"

FILES_${PN} += "${OE_QMAKE_PATH_QML}/Eos/*"

PACKAGES += "${PN}-examples"
FILES_${PN}-examples += "${webos_applicationsdir}/*"

# unit-tests
PACKAGES =+ "${PN}-tests"
FILES_${PN}-tests += "${datadir}/booster/tests/*"

# SDK tools
PACKAGES += "${PN}-tools"
FILES_${PN}-tools += "${webos_sdkdir}/*"

# we don't provide cmake tests
EXTRA_QMAKEVARS_POST += "CONFIG-=create_cmake"
