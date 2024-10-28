# Copyright (c) 2012-2024 LG Electronics, Inc.

SUMMARY = "Library for dynamically generating webOS system bus role files for webOS JavaScript services"
SECTION = "webos/libs"
AUTHOR = "Rajesh Gopu I.V <rajeshgopu.iv@lge.com>"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57 \
    file://oss-pkg-info.yaml;md5=2bdfe040dcf81b4038370ae96036c519 \
"

DEPENDS = "glib-2.0"

WEBOS_VERSION = "2.1.0-6_c4383f7d65b4cda9ba72553144dda9280cea9ec8"
PR = "r7"

inherit webos_component
inherit webos_public_repo
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_library

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

FILES:${PN} += "${datadir}/rolegen"
