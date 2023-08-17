# Copyright (c) 2017-2023 LG Electronics, Inc.

SUMMARY = "AVOutputd adaptation layer (AVAL) API definition and test harness"
SECTION = "webos/libs"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=89aea4e17d99a7cacdbeed46a0096b10"

DEPENDS = "glib-2.0"

WEBOS_VERSION = "1.0.0-1_46828b6f12a027d70eab3e3ae720b3f8be1de261"
PR = "r0"

inherit webos_component
inherit webos_pkgconfig
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_public_repo

# http://gpro.lge.com/c/webosose/avoutput-adaptation-layer-api/+/354361 Fix build with gcc-13
# http://gpro.lge.com/c/webosose/avoutput-adaptation-layer-api/+/354362 LICENSE: add Apache-2.0 License
SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE} \
    file://0001-Fix-build-with-gcc-13.patch \
    file://0002-LICENSE-add-Apache-2.0-License.patch \
"
S = "${WORKDIR}/git"
