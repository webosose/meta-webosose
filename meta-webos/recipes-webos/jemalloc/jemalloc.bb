# Copyright (c) 2012-2020 LG Electronics, Inc.

SUMMARY = "webOS of the open-source FreeBSD memory allocation library"
AUTHOR = "Rama Krishna <rama.krishna@lge.com>"
SECTION = "libs"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://README.md;beginline=94;md5=b159f6c7121460dba2c5965602bc3268 \
                    file://oss-pkg-info.yaml;md5=2bdfe040dcf81b4038370ae96036c519 \
"

WEBOS_VERSION = "0.20080828a-0webos9-2_1155ed6a90c04c6d0349cc1184744254d5d37949"
PR = "r3"

inherit webos_upstream_from_repo
inherit webos_public_repo
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_library

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

SRC_URI += "file://0001-Fix-build-with-glibc-2.32.patch"
