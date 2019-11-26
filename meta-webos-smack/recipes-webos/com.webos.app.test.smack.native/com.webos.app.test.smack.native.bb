# Copyright (c) 2019 LG Electronics, Inc.

SUMMARY = "Native Qt App"
SECTION = "webos/apps"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

WEBOS_VERSION = "1.0.0-1_083b69c1827edf5214dd78d8ac0cab96407b000b"

inherit webos_enhanced_submissions
inherit webos_qmake5
inherit webos_app

SRC_URI = "git://gpro.lge.com/webos/${BPN};protocol=ssh"
PR = "r0"
S = "${WORKDIR}/git"

DEPENDS = "qtbase qt-features-webos qtdeclarative glib-2.0"
RDEPENDS_${PN} += "qml-webos-framework qml-webos-bridge"

OE_QMAKE_PATH_HEADERS = "${OE_QMAKE_PATH_QT_HEADERS}"

FILES_${PN} += "${webos_applicationsdir}"
