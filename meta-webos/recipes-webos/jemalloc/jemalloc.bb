# Copyright (c) 2012-2018 LG Electronics, Inc.

SUMMARY = "webOS of the open-source FreeBSD memory allocation library"
AUTHOR = "Rama Krishna <rama.krishna@lge.com>"
SECTION = "libs"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://README.md;beginline=94;md5=b159f6c7121460dba2c5965602bc3268"

WEBOS_VERSION = "0.20080828a-0webos9-1_88bcdb69b3dd3dcd7814a0b63360d7e5e490840a"
PR = "r1"

inherit webos_upstream_from_repo
inherit webos_public_repo
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_library

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"
