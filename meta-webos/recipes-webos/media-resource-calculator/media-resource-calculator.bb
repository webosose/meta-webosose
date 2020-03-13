# Copyright (c) 2017-2020 LG Electronics, Inc.

DESCRIPTION = "Media Resource Calculator for webOS"
AUTHOR = "Bhooshan Supe <bhooshan.supe@lge.com>"
SECTION = "webos/libs"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

DEPENDS = "libpbnjson"

EXTRA_OECMAKE += "-DNO_TEST=1"

WEBOS_VERSION = "1.0.0-4_3711202dc9e4eac10241b63d5f2baac876083421"
PR = "r4"

inherit webos_component
inherit webos_enhanced_submissions
inherit webos_cmake
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
COMPATIBLE_MACHINE = "^qemux86$|^raspberrypi3$|^raspberrypi3-64$|^raspberrypi4$|^raspberrypi4-64$"
