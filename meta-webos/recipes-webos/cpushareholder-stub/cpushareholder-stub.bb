# Copyright (c) 2012-2025 LG Electronics, Inc.

SUMMARY = "Stubbed implementation of the webOS CPU shares scripts"
AUTHOR = "Rajesh Gopu I.V <rajeshgopu.iv@lge.com>"
SECTION = "webos/base"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57 \
    file://oss-pkg-info.yaml;md5=2bdfe040dcf81b4038370ae96036c519 \
"

RPROVIDES:${PN} = "cpushareholder"

WEBOS_VERSION = "2.0.1-4_bef23b732f076008ad1eec5a7b77e62d0e354427"
PR = "r5"

inherit webos_component
inherit webos_public_repo
inherit webos_enhanced_submissions
inherit webos_arch_indep
inherit webos_cmake
inherit webos_program

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"
