# Copyright (c) 2013-2020 LG Electronics, Inc.

SUMMARY = "webOS QML LS2 bridge"
AUTHOR = "Anupam Kaul <anupam.kaul@lge.com>"
SECTION = "webos/libs"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

DEPENDS = "qtdeclarative luna-service2 glib-2.0"
RDEPENDS_${PN} += "qml-webos-components"

WEBOS_VERSION = "1.0.0-114_a25fdf68a95a9f2a5e7a3f6d3db16c8b369d5eb8"
PR = "r12"

inherit webos_qmake5
inherit webos_enhanced_submissions
inherit webos_qmllint
inherit webos_public_repo

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

OE_QMAKE_PATH_HEADERS = "${OE_QMAKE_PATH_QT_HEADERS}"

# Perform extra QML validation
WEBOS_QMLLINT_EXTRA_VALIDATION = "1"

FILES_${PN} += "${OE_QMAKE_PATH_QML}/WebOSServices/*"

do_install_append() {
    # until /usr/lib/qt5/qml paths in .qml files are updated to respect OE_QMAKE_PATH_QML
    ln -snf . ${D}/${libdir}/qt5
}
FILES_${PN} += "${libdir}/qt5"
