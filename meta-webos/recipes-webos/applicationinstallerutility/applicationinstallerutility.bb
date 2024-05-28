# Copyright (c) 2013-2024 LG Electronics, Inc.

SUMMARY="The Application Installer Utility supports the installing and removing of applications on a HP webOS device."
AUTHOR = "Guruprasad KN <guruprasad.kn@lge.com>"
SECTION = "webos/apps"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://LICENSE;md5=89aea4e17d99a7cacdbeed46a0096b10 \
    file://oss-pkg-info.yaml;md5=2bdfe040dcf81b4038370ae96036c519 \
"

DEPENDS = "pmloglib openssl glib-2.0"

WEBOS_VERSION = "3.0.0-5_3b7116a9cdd5e118bd1219aaaf44788da01054b1"
PR = "r2"

inherit webos_component
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_program
inherit webos_machine_impl_dep
inherit webos_public_repo

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"
