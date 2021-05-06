# Copyright (c) 2021 LG Electronics, Inc.

SUMMARY = "Webos UwbService"
AUTHOR = "Bojung.Ko <bojung.ko@lge.com>"
SECTION = "webos/services"
LICENSE = "CLOSED"

DEPENDS= "glib-2.0 luna-service2 pmloglib libpbnjson"

WEBOS_VERSION = "1.0.0-2_b7957ea6fe4c00c81c464f3a7bc3cb4374f71080"
PR = "r0"

inherit webos_component
inherit webos_public_repo
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_system_bus

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"
