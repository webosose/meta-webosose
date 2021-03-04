# Copyright (c) 2021 LG Electronics, Inc.

SUMMARY = "webOS QML app components"
AUTHOR = "Jongson Kim <jongson.kim@lge.com>"
SECTION = "webos/libs"
LICENSE = "CLOSED"

DEPENDS = "qtdeclarative luna-service2 glib-2.0"

WEBOS_VERSION = "1.0.0-1_da4f2d58360ee0d3445833941dc235d19ceab524"
PR = "r0"

inherit webos_qmake5
inherit webos_enhanced_submissions
inherit webos_public_repo

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

QMAKE_PROFILES = "${S}/qml-app-components.pro"
OE_QMAKE_PATH_HEADERS = "${OE_QMAKE_PATH_QT_HEADERS}"

FILES_${PN} += "${OE_QMAKE_PATH_QML}/QmlAppComponents/*"
