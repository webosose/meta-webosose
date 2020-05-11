# Copyright (c) 2013-2020 LG Electronics, Inc.

SUMMARY = "Shareable QML components for webOS"
AUTHOR = "Anupam Kaul <anupam.kaul@lge.com>"
SECTION = "webos/libs"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

DEPENDS = "qtdeclarative pmloglib librdx qt-features-webos"

WEBOS_VERSION = "1.0.0-47_2d45e4d13628625249d0908e541d6a817ea32b14"
PR = "r12"

inherit webos_qmake5
inherit webos_machine_impl_dep
inherit webos_enhanced_submissions
inherit webos_lttng
inherit webos_qmllint
inherit webos_public_repo

# TODO: move to WEBOS_GIT_REPO_COMPLETE
# Once the repo will go public this recipe should be moved to meta-webos
# but until that happens we need to clone from gpro
SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

OE_QMAKE_PATH_HEADERS = "${OE_QMAKE_PATH_QT_HEADERS}"

# Perform extra QML validation
WEBOS_QMLLINT_EXTRA_VALIDATION = "1"

# Enable LTTng tracing capability when enabled in webos_lttng class
EXTRA_QMAKEVARS_PRE += "${@ 'CONFIG+=lttng' if '${WEBOS_LTTNG_ENABLED}' == '1' else '' }"

# Base directory of localization data
EXTRA_QMAKEVARS_PRE += "WEBOS_QT_LOCALIZATION_QM_BASEDIR=${datadir}/qml/locales"

FILES_${PN} += "${OE_QMAKE_PATH_QML}"
