# Copyright (c) 2014-2018 LG Electronics, Inc.

SUMMARY = "umediaserver configs installation"
AUTHOR = "Peter Nordström <peter.nordstrom@lge.com>"
SECTION = "webos/base"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

WEBOS_VERSION = "1.0.0-1_012e133b61b5e7b1416dfa102b6c534a454d52b4"
PR = "r6"

inherit webos_cmake
inherit webos_machine_dep
inherit webos_enhanced_submissions
inherit webos_distro_dep
inherit webos_distro_variant_dep
inherit webos_filesystem_paths
inherit webos_public_repo

EXTRA_OECMAKE += "-DWEBOS_INSTALL_CONFCAPSDIR:STRING=${webos_frameworksdir}"

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

FILES_${PN} += "${webos_frameworksdir}/umediaserver/*"
