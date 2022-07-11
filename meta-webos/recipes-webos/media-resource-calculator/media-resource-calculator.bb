# Copyright (c) 2017-2022 LG Electronics, Inc.

DESCRIPTION = "Media Resource Calculator for webOS"
AUTHOR = "Kalaimani K <kalaimani.k@lge.com>"
SECTION = "webos/libs"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10 \
    file://oss-pkg-info.yaml;md5=2bdfe040dcf81b4038370ae96036c519 \
"

DEPENDS = "libpbnjson"

EXTRA_OECMAKE += "-DNO_TEST=1"

WEBOS_VERSION = "1.0.0-9_d685d5e7c8828b252dba18833046a7c4fcd97f95"
PR = "r6"

inherit webos_component
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_pkgconfig
inherit webos_machine_dep
inherit webos_distro_variant_dep
inherit webos_public_repo

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

# Since submissions 47 this isn't usable for any other MACHINE than
# raspberrypi3
# raspberrypi3-64
# raspberrypi4
# raspberrypi4-64
# qemux86
# qemux86-64
COMPATIBLE_MACHINE = "^qemux86$|^qemux86-64$|^raspberrypi3$|^raspberrypi3-64$|^raspberrypi4$|^raspberrypi4-64$"
