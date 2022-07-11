# Copyright (c) 2013-2022 LG Electronics, Inc.

SUMMARY = "webOS logging library - private interface"
AUTHOR = "Rajesh Gopu I.V <rajeshgopu.iv@lge.com>"
SECTION = "webos/libs"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

WEBOS_VERSION = "3.3.0-2_75eef528791b73b8bc4f84cf522c6a1c533edc32"
PR = "r1"

inherit webos_component
inherit webos_public_repo
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_pkgconfig

# B needs to be different from that of pmloglib so there's
# no collision in the case of local development.
B = "${S}/build-private"
EXTRA_OECMAKE += "-DBUILD_PRIVATE=ON"

WEBOS_REPO_NAME = "pmloglib"
SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"
