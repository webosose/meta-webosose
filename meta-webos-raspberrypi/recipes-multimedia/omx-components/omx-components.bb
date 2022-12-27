# Copyright (c) 2017-2023 LG Electronics, Inc.

SUMMARY = "OMX Components implementation for Raspberry Pi"
AUTHOR = "Purushottam Narayana <purushottam.narayana@lge.com>"
SECTION = "webos/libs"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

DEPENDS = "glib-2.0 pmloglib libpbnjson virtual/libomxil alsa-lib libdrm ffmpeg"

WEBOS_VERSION = "1.0.0-1_c723e9917bc667661efaceb5b161dcf3e7bd7cf4"
PR = "r2"

inherit webos_component
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_library
inherit webos_test_provider
inherit webos_public_repo

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

COMPATIBLE_MACHINE = "^rpi$"
