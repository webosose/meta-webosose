# Copyright (c) 2021 LG Electronics, Inc.

SUMMARY = "webOS QML app components"
AUTHOR = "Jongson Kim <jongson.kim@lge.com>"
SECTION = "webos/libs"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
  file://LICENSE;md5=89aea4e17d99a7cacdbeed46a0096b10 \
"

DEPENDS = "qtdeclarative luna-service2 glib-2.0"
DEPENDS_append = " ${@ 'qtshadertools-native' if d.getVar('QT_VERSION', True) == '6' else '' }"

WEBOS_VERSION = "1.0.0-4_e147b3bc8dc6a5edbcf5f4431611bd4fcf520e3b"
PR = "r4"

inherit webos_qmake6
inherit webos_pkgconfig
inherit webos_enhanced_submissions
inherit webos_public_repo

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

QMAKE_PROFILES = "${S}/qml-app-components.pro"
OE_QMAKE_PATH_HEADERS = "${OE_QMAKE_PATH_QT_HEADERS}"

FILES_${PN} += "${OE_QMAKE_PATH_QML}/QmlAppComponents/*"
