# Copyright (c) 2013-2018 LG Electronics, Inc.

SUMMARY="The Application Installer Utility supports the installing and removing of applications on a HP webOS device."
AUTHOR = "Seokjun Lee <sseokjun.lee@lge.com>"
SECTION = "webos/apps"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

DEPENDS = "pmloglib openssl glib-2.0"

WEBOS_VERSION = "3.0.0-1_d137e429f88d034db4646f045635a19e6879d6ba"
PR = "r1"

inherit webos_component
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_program
inherit webos_machine_impl_dep
inherit webos_public_repo

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"
