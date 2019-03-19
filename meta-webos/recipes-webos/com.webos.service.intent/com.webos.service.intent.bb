# Copyright (c) 2020 LG Electronics, Inc.

SUMMARY = "Intent Manager"
AUTHOR = "Sangwoo Kang <sangwoo82.kang@lge.com>"
SECTION = "webos/base"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=89aea4e17d99a7cacdbeed46a0096b10"

DEPENDS = "glib-2.0 luna-service2 libpbnjson"

WEBOS_VERSION = "1.0.0-5_bf7b8df129e1207971d4fde15ff7bb7cc5e4f6ad"
PR = "r0"

inherit webos_component
inherit webos_cmake
inherit webos_enhanced_submissions
inherit webos_daemon
inherit webos_system_bus
inherit webos_distro_dep
inherit webos_distro_variant_dep
inherit webos_public_repo

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"
