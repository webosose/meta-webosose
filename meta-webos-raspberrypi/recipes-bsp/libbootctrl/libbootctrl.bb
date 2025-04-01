# Copyright (c) 2023-2025 LG Electronics, Inc.

SUMMARY = "Boot contoller library for A/B update"
AUTHOR = "JeongBong Seo <jb.seo@lge.com>"
SECTION = "webos/base"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=89aea4e17d99a7cacdbeed46a0096b10"

inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_library
inherit webos_public_repo

DEPENDS = "libubootenv"
RDEPENDS:${PN} = "u-boot-env"

PR = "r1"
WEBOS_VERSION = "1.0.0-2_bfd94595f8dd03972c8070b094e4196e9c8ff19a"

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"

S = "${WORKDIR}/git"

# move test files into -tests
PACKAGES:prepend = "${PN}-tests "
FILES:${PN}-tests += "${bindir}/*.test"

# fix depends problem when populate_sdk
ALLOW_EMPTY:${PN} = "1"
RDEPENDS:${PN}-dev += "${PN}-staticdev"
