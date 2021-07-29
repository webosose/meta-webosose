# Copyright (c) 2017-2020 LG Electronics, Inc.

SUMMARY = "webOS connman adapter support library"
AUTHOR = "Seokhee Lee <seokhee.lee@lge.com>"
SECTION = "webos/libs"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10 \
    file://oss-pkg-info.yaml;md5=1c44bb8384dc6f3479834afcdfa98054 \
"

WEBOS_VERSION = "1.0.0-4_a59127baf2ddebc99874f714c9fe5528772e94d1"
PR = "r2"

DEPENDS = "glib-2.0 luna-service2 libpbnjson pmloglib luna-prefs wca-support-api"

RDEPENDS:${PN} = "iw"

inherit webos_component
inherit webos_enhanced_submissions
inherit webos_machine_dep
inherit webos_cmake
inherit webos_library
inherit webos_distro_dep
inherit webos_machine_impl_dep
inherit webos_public_repo

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"
