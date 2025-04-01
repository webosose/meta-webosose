# Copyright (c) 2020-2025 LG Electronics, Inc.

SUMMARY = "Power manager service handles device suspend/resume/display states"
AUTHOR = "Yogish S <yogish.s@lge.com>"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"
SECTION = "webos/libs"

DEPENDS = "luna-service2 glib-2.0 libpmscore virtual/pmssupportreference pmloglib libpbnjson nyx-lib"

WEBOS_VERSION = "1.0.0-13_1d3601a6701d213e8d67d3db0a2913d8ebb4073b"
PR = "r3"

inherit webos_component
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_daemon
inherit webos_system_bus
inherit webos_public_repo
inherit webos_library

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

inherit webos_systemd
WEBOS_SYSTEMD_SERVICE = "powermgr.service"
