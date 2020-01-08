# Copyright (c) 2014-2020 LG Electronics, Inc.

SUMMARY = "QML widgets and runtime framework for webOS apps"
AUTHOR = "Mikko Levonmaa <mikko.levonmaa@lge.com>"
SECTION = "webos/base"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

DEPENDS = "qt-features-webos qtdeclarative qtwayland-webos pmloglib luna-service2"
RDEPENDS_${PN} = "qtgraphicaleffects-qmlplugins"

RPROVIDES_${PN}-examples = " \
    eos.bare \
    eos.widgetgallery \
"

WEBOS_VERSION = "1.0.0-144_b2d8fe22e495f0b5a1a9d97774b43e60dca81ea5"
PR = "r28"

inherit webos_qmake5
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
