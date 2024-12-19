# Copyright (c) 2019-2024 LG Electronics, Inc.

SUMMARY = "Surface Manager for webOS Auto Reference UX"
AUTHOR = "Elvis Lee <kwangwoong.lee@lge.com>"
SECTION = "webos/base"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://LICENSE;md5=89aea4e17d99a7cacdbeed46a0096b10 \
    file://oss-pkg-info.yaml;md5=1bbf047e5c70344c074aaaa58a668952 \
"

DEPENDS = "luna-surfacemanager qt-features-webos"
RDEPENDS:${PN} = " \
    luna-surfacemanager-base \
    qtbase-plugins-webos \
"

WEBOS_VERSION = "0.0.1-46_1440a606c4fb7a3c3b22e4957c883a9969356f74"
PR = "r8"

inherit webos_qmake6
inherit webos_enhanced_submissions
inherit webos_public_repo
inherit webos_qmllint
inherit webos_system_bus
inherit features_check
ANY_OF_DISTRO_FEATURES = "vulkan opengl"

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

OE_QMAKE_PATH_HEADERS = "${OE_QMAKE_PATH_QT_HEADERS}"

# Perform extra QML validation
WEBOS_QMLLINT_EXTRA_VALIDATION = "1"

WEBOS_SYSTEM_BUS_SKIP_DO_TASKS = "1"

FILES:${PN} += " \
    ${OE_QMAKE_PATH_QML}/WebOSCompositor \
    ${OE_QMAKE_PATH_PLUGINS}/compositor \
"
