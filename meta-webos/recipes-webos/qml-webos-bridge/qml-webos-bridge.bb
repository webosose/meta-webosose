# Copyright (c) 2013-2022 LG Electronics, Inc.

SUMMARY = "webOS QML LS2 bridge"
AUTHOR = "Elvis Lee <kwangwoong.lee@lge.com>"
SECTION = "webos/libs"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://LICENSE;md5=89aea4e17d99a7cacdbeed46a0096b10 \
    file://oss-pkg-info.yaml;md5=5d5cc0ac1cf0f514284f99a9777cfe5c \
"

DEPENDS = "qtdeclarative luna-service2 glib-2.0"
RDEPENDS:${PN} += "qml-webos-components"

WEBOS_VERSION = "1.0.0-129_6f44cfcb4f33f3f8d6997c5bbcf88ba7bb9e5e3a"
PR = "r18"

inherit webos_qmake6
inherit webos_pkgconfig
inherit webos_enhanced_submissions
inherit webos_qmllint
inherit webos_public_repo

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

OE_QMAKE_PATH_HEADERS = "${OE_QMAKE_PATH_QT_HEADERS}"

# Perform extra QML validation
WEBOS_QMLLINT_EXTRA_VALIDATION = "1"

FILES:${PN} += "${OE_QMAKE_PATH_QML}/WebOSServices/*"
