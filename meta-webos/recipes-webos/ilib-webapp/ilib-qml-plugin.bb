# Copyright (c) 2017-2018 LG Electronics, Inc.

SUMMARY = "iLib Qml loader"
AUTHOR = "Goun Lee <goun.lee@lge.com>"
SECTION = "libs/qtplugin"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=578915003022e6b28128e6696dddbb46"

DEPENDS = "qtdeclarative"

WEBOS_VERSION = "11.0.0-1_0d849d3ad085b610e66e4a9267190f3a6cf5bf38"
PR = "r2"

inherit webos_qmake5
inherit webos_enhanced_submissions
inherit webos_public_repo

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

FILE_READER_PROJECT = "filereader-webos.pro"
QMAKE_PROFILES = "${S}/${FILE_READER_PROJECT}"

OE_QMAKE_PATH_HEADERS = "${OE_QMAKE_PATH_QT_HEADERS}"

FILES_${PN} += "${OE_QMAKE_PATH_QML}/*"
