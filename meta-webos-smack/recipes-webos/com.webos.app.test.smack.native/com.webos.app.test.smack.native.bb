# Copyright (c) 2019-2023 LG Electronics, Inc.

SUMMARY = "Native Qt App"
SECTION = "webos/apps"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

PR = "r4"

WEBOS_VERSION = "1.0.0-1_083b69c1827edf5214dd78d8ac0cab96407b000b"

inherit webos_enhanced_submissions
inherit webos_qmake6
inherit webos_pkgconfig
inherit webos_app
inherit webos_public_repo

# http://gpro.lge.com/c/webosose/com.webos.app.test.smack.native/+/349091 Fix build with qt-6
SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE} \
    file://0001-Fix-build-with-qt-6.patch \
"
S = "${WORKDIR}/git"

DEPENDS = "qtbase qt-features-webos qtdeclarative glib-2.0"
RDEPENDS:${PN} += "qml-webos-framework qml-webos-bridge"

OE_QMAKE_PATH_HEADERS = "${OE_QMAKE_PATH_QT_HEADERS}"

FILES:${PN} += "${webos_applicationsdir}"
