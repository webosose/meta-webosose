# Copyright (c) 2013-2020 LG Electronics, Inc.

SUMMARY = "Tellurium service for mediating between telluriumnub and pytell."
AUTHOR = "Tigran Avanesov <tigran.avanesov@lge.com>"
SECTION = "webos/tools"
LICENSE = "CLOSED"
DEPENDS = "glib-2.0 luna-service2 libpbnjson pmloglib"

WEBOS_VERSION = "2.0.0-16_b8fbcec31536a734f09ac2e7db72f6a70050613a"
PR = "r3"

inherit webos_component
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_daemon
inherit webos_system_bus

SRC_URI = "${WEBOS_PRO_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"
