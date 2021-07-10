# Copyright (c) 2013-2021 LG Electronics, Inc.

SUMMARY = "Common Qt features for webOS components"
AUTHOR = "Anupam Kaul <anulam.kaul@lge.com>"
SECTION = "webos/base"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10 \
    file://oss-pkg-info.yaml;md5=35b2f0d27bf4833f2764dfe176fa1e9c \
"

DEPENDS = "qtbase"

WEBOS_VERSION = "1.0.0-49_3d1cd246bf9f2898f6eda02548619aad63f96830"
PR = "r7"

inherit webos_qmake6
inherit webos_enhanced_submissions
inherit webos_public_repo

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

FILES_${PN}-dev += "${OE_QMAKE_PATH_QT_ARCHDATA}/mkspecs"

# An empty package is needed to satisfy package dependencies when building bdk.
ALLOW_EMPTY_${PN} = "1"

BBCLASSEXTEND = "native"

do_configure_class-native() {
    ${OE_QMAKE_QMAKE} ${OE_QMAKE_DEBUG_OUTPUT} -r ${S}/tools/generate_qmap
}

do_install_class-native() {
    oe_runmake install INSTALL_ROOT=${D}
}
