# Copyright (c) 2021 LG Electronics, Inc.

SUMMARY = "webOS QML app components"
AUTHOR = "Jongson Kim <jongson.kim@lge.com>"
SECTION = "webos/libs"
LICENSE = "CLOSED"

DEPENDS = "qtdeclarative luna-service2 glib-2.0"
DEPENDS_append = " ${@ 'qtshadertools-native' if d.getVar('QT_VERSION', True) == '6' else '' }"

WEBOS_VERSION = "1.0.0-2_fbf3fc0c2f22f9a283a33b3469fd187598609ca0"
PR = "r2"

inherit webos_qmake6
inherit webos_enhanced_submissions
inherit webos_public_repo

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

QMAKE_PROFILES = "${S}/qml-app-components.pro"
OE_QMAKE_PATH_HEADERS = "${OE_QMAKE_PATH_QT_HEADERS}"

FILES_${PN} += "${OE_QMAKE_PATH_QML}/QmlAppComponents/*"
