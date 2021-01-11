# Copyright (c) 2020-2021 LG Electronics, Inc.

SUMMARY = "Power manager service handles device suspend/resume/display states"
AUTHOR = "Abhsiehk Srivastava <abhishek.srivastava@lge.com>"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"
SECTION = "webos/libs"

DEPENDS = "luna-service2 glib-2.0 libpmscore virtual/pmssupportreference pmloglib libpbnjson nyx-lib"

WEBOS_VERSION = "1.0.0-9_7c373ff36ee8474e40e9c2ff9df5814ab0f694cd"
PR = "r1"

inherit webos_component
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_daemon
inherit webos_system_bus
inherit webos_public_repo
inherit webos_library
inherit webos_distro_dep

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"
