# Copyright (c) 2012-2019 LG Electronics, Inc.

DESCRIPTION = "CMake modules used by webOS"
LICENSE = "Apache-2.0"
SECTION = "webos/devel/tools"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

WEBOS_VERSION = "1.6.3-3_a7010cb34dd263b63369c83a530890026b8c5a25"
PR = "r2"

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

# From http://gpro.lge.com/255718
SRC_URI += " \
    file://0001-_webos_init_install_vars-add-variables-originally-im.patch \
"
