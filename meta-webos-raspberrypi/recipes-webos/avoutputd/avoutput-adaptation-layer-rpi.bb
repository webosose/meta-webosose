# Copyright (c) 2017-2018 LG Electronics, Inc.

SUMMARY = "AVAL API implementation for Raspberry Pi"
AUTHOR = "Soumya Aithal <soumya.aithal@lge.com>"
SECTION = "webos/libs"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

PROVIDES = "aval-impl"

inherit webos_component
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_library
inherit webos_test_provider
inherit webos_public_repo

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"

S = "${WORKDIR}/git"

DEPENDS = "udev glib-2.0 pmloglib libpbnjson ls2-helpers alsa-lib libdrm avoutput-adaptation-layer-api"

WEBOS_VERSION = "1.0.0-1_5373de0f1d195a9081ae85770d81283c7a717580"
PR = "r4"

COMPATIBLE_MACHINE = "^rpi$"
