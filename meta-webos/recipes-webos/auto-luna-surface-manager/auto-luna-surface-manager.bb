# Copyright (c) 2019-2020 LG Electronics, Inc.

SUMMARY = "Surface Manager for webOS Auto Reference UX"
AUTHOR  = "Jaeyoon Jung <jaeyoon.jung@lge.com>"
SECTION = "webos/base"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10 \
    file://oss-pkg-info.yaml;md5=49b716ba64042488414ba9a376607b70 \
"

DEPENDS = "luna-surfacemanager qt-features-webos"
RDEPENDS_${PN} = " \
    luna-surfacemanager-base \
"

WEBOS_VERSION = "0.0.1-27_20ed52a5757e58976ee4508f4176690a8ce1baeb"
PR = "r3"

inherit webos_qmake5
inherit webos_enhanced_submissions
inherit webos_public_repo
inherit webos_qmllint
inherit webos_system_bus

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

OE_QMAKE_PATH_HEADERS = "${OE_QMAKE_PATH_QT_HEADERS}"

# Perform extra QML validation
WEBOS_QMLLINT_EXTRA_VALIDATION = "1"

WEBOS_SYSTEM_BUS_SKIP_DO_TASKS = "1"

FILES_${PN} += " \
    ${OE_QMAKE_PATH_QML}/WebOSCompositor \
    ${OE_QMAKE_PATH_PLUGINS}/compositor \
"
