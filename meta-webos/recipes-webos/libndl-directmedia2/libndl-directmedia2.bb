# Copyright (c) 2015-2018 LG Electronics, Inc.

SUMMARY = "Direct media2 library"
AUTHOR  = "Minjae Kim <nate.kim@lge.com>"
SECTION = "webos/base"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

DEPENDS = "boost gtest glib-2.0 luna-service2 ffmpeg libpbnjson pmloglib umediaserver media-resource-calculator virtual/libomxil"

WEBOS_VERSION = "1.0.0-48_3b93fe86ebde6f1834fd4e16af9b3fc9e7658ea2"
PR = "r14"

inherit webos_cmake
inherit webos_machine_dep
inherit webos_enhanced_submissions
inherit webos_system_bus
inherit webos_pkgconfig
inherit webos_distro_variant_dep
inherit webos_public_repo

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"
WEBOS_SYSTEM_BUS_FILES_LOCATION = "${S}/files/sysbus"

COMPATIBLE_MACHINE = "(^$)"
