# Copyright (c) 2019 LG Electronics, Inc.

SUMMARY = "Surface Manager for webOS Auto Reference UX"
AUTHOR  = "Jaeyoon Jung <jaeyoon.jung@lge.com>"
SECTION = "webos/base"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

DEPENDS = "luna-surfacemanager qt-features-webos"
RDEPENDS_${PN} = " \
    luna-surfacemanager-base \
"

WEBOS_VERSION = "0.0.1-4_32058257b5f23bd58233328f9e1e184a20840913"
PR = "r0"

inherit webos_qmake5
inherit webos_enhanced_submissions
inherit webos_public_repo
inherit webos_qmllint
inherit webos_system_bus

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

OE_QMAKE_PATH_HEADERS = "${OE_QMAKE_PATH_QT_HEADERS}"

WEBOS_SYSTEM_BUS_SKIP_DO_TASKS = "1"

FILES_${PN} += " \
    ${OE_QMAKE_PATH_QML}/WebOSCompositor \
"
