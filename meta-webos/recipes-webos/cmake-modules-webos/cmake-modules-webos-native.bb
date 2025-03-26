# Copyright (c) 2012-2025 LG Electronics, Inc.

DESCRIPTION = "CMake modules used by webOS"
LICENSE = "Apache-2.0"
AUTHOR = "Vijaya Sundaram <vijaya.sundaram@lge.com>"
SECTION = "webos/devel/tools"
LIC_FILES_CHKSUM = "file://LICENSE;md5=89aea4e17d99a7cacdbeed46a0096b10"

WEBOS_VERSION = "1.6.5-8_a7603cd37dcafb5d27310f3960a007a6d4ce71e9"
PR = "r0"

inherit webos_component
inherit webos_public_repo
inherit webos_enhanced_submissions
inherit webos_cmake
inherit native

WEBOS_CMAKE_DEPENDS = ""

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

do_compile() {
     :
}
