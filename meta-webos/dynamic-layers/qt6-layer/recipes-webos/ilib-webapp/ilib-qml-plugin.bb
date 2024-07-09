# Copyright (c) 2017-2024 LG Electronics, Inc.

SUMMARY = "iLib Qml loader"
AUTHOR = "Seonmi Jin <seonmi1.jin@lge.com>"
SECTION = "libs/qtplugin"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57 \
    file://oss-pkg-info.yaml;md5=391133fb0a5ff786d7d30c07467b5d21 \
"

DEPENDS = "qtdeclarative"
RDEPENDS:${PN} += "ilib-webapp"

WEBOS_VERSION = "11.0.0-6_bdf1beb39a1326243eac0797791f099ca4f1c5ae"
PR = "r7"

inherit webos_qmake6
inherit webos_enhanced_submissions
inherit webos_public_repo

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

FILE_READER_PROJECT = "filereader-webos.pro"
QMAKE_PROFILES = "${S}/${FILE_READER_PROJECT}"

OE_QMAKE_PATH_HEADERS = "${OE_QMAKE_PATH_QT_HEADERS}"

FILES:${PN} += "${OE_QMAKE_PATH_QML}/*"
