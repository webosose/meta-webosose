# Copyright (c) 2012-2024 LG Electronics, Inc.

SUMMARY = "An extensible object oriented component used to add service components to webOS"
AUTHOR = "Rajesh Gopu I.V <rajeshgopu.iv@lge.com>"
SECTION = "webos/devel"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57 \
    file://oss-pkg-info.yaml;md5=2bdfe040dcf81b4038370ae96036c519 \
"
DEPENDS = "librolegen glib-2.0 libpbnjson luna-service2"

WEBOS_VERSION = "2.0.0-6_279dbc2015b045dddd1bc0bcee4c761b718618d6"
PR = "r6"

inherit webos_component
inherit webos_public_repo
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_pkgconfig

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

ALLOW_EMPTY:${PN} = "1"
