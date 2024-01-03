# Copyright (c) 2017-2023 LG Electronics, Inc.

SUMMARY = "webOS connman adapter support API"
AUTHOR = "Muralidhar N <muralidhar.n@lge.com>"
SECTION = "webos/libs"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://LICENSE;md5=2763f3ed850f8412903ea776e0526bea \
    file://oss-pkg-info.yaml;md5=6846cc431bc5281393713ebd4300401d \
"

DEPENDS = "libpbnjson luna-service2"

WEBOS_VERSION = "1.0.0-5_117f8854f0682263a999d50b1f146b7b6d0d288f"
PR = "r3"

inherit webos_component
inherit webos_enhanced_submissions
inherit webos_pkgconfig
inherit webos_cmake
inherit webos_public_repo

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"
