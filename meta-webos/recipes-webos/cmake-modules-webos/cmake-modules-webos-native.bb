# Copyright (c) 2012-2023 LG Electronics, Inc.

DESCRIPTION = "CMake modules used by webOS"
LICENSE = "Apache-2.0"
AUTHOR = "Vijaya Sundaram <vijaya.sundaram@lge.com>"
SECTION = "webos/devel/tools"
LIC_FILES_CHKSUM = "file://LICENSE;md5=89aea4e17d99a7cacdbeed46a0096b10"

WEBOS_VERSION = "1.6.3-6_b22ef30599f9af4c5b146538148552b495320ba3"
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

# [http://gpro.lge.com/c/webosose/cmake-modules-webos/+/255718 _webos_init_install_vars: add variables originally imported from pro]
# [http://gpro.lge.com/c/webosose/cmake-modules-webos/+/255719 _webos_init_install_vars: change webos_testsdir default value]
SRC_URI += " \
    file://0001-_webos_init_install_vars-add-variables-originally-im.patch \
    file://0002-_webos_init_install_vars-change-webos_testsdir-defau.patch \
"
