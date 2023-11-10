# Copyright (c) 2012-2023 LG Electronics, Inc.

DESCRIPTION = "CMake modules used by webOS"
LICENSE = "Apache-2.0"
AUTHOR = "Vijaya Sundaram <vijaya.sundaram@lge.com>"
SECTION = "webos/devel/tools"
LIC_FILES_CHKSUM = "file://LICENSE;md5=89aea4e17d99a7cacdbeed46a0096b10"

WEBOS_VERSION = "1.6.3-7_4c1b92bb80b71cae7df829bb9fabd5bc7c204d08"
PR = "r3"

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

