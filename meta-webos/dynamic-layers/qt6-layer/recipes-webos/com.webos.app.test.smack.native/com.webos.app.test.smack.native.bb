# Copyright (c) 2019-2025 LG Electronics, Inc.

SUMMARY = "Native Qt App"
SECTION = "webos/apps"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=89aea4e17d99a7cacdbeed46a0096b10"

PR = "r7"

WEBOS_VERSION = "1.0.0-2_e70429b303f285bde70c1610b555f6ba79c21cd8"

inherit webos_enhanced_submissions
inherit webos_qmake6
inherit webos_pkgconfig
inherit webos_app
inherit webos_public_repo
inherit features_check
ANY_OF_DISTRO_FEATURES = "vulkan opengl"

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

DEPENDS = "qtbase qt-features-webos qtdeclarative glib-2.0"
RDEPENDS:${PN} += "qml-webos-framework qml-webos-bridge"

OE_QMAKE_PATH_HEADERS = "${OE_QMAKE_PATH_QT_HEADERS}"

FILES:${PN} += "${webos_applicationsdir}"
