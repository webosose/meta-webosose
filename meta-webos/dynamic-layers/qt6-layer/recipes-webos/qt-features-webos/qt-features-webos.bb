# Copyright (c) 2013-2024 LG Electronics, Inc.

SUMMARY = "Common Qt features for webOS components"
AUTHOR = "Elvis Lee <kwangwoong.lee@lge.com>"
SECTION = "webos/base"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://LICENSE;md5=89aea4e17d99a7cacdbeed46a0096b10 \
    file://oss-pkg-info.yaml;md5=35b2f0d27bf4833f2764dfe176fa1e9c \
"

DEPENDS = "qtbase"

WEBOS_VERSION = "1.0.0-57_eee9f70b1c7242e8f5b831f765751f3858f363c7"
PR = "r8"

inherit webos_qmake6
inherit webos_enhanced_submissions
inherit webos_public_repo

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

FILES:${PN}-dev += "${OE_QMAKE_PATH_QT_ARCHDATA}/mkspecs"

# An empty package is needed to satisfy package dependencies when building bdk.
ALLOW_EMPTY:${PN} = "1"

BBCLASSEXTEND = "native"

do_configure:class-native() {
    ${OE_QMAKE_QMAKE} ${OE_QMAKE_DEBUG_OUTPUT} -r ${S}/tools/generate_qmap
}

do_install:class-native() {
    oe_runmake install INSTALL_ROOT=${D}
}
