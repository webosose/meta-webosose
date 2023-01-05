# Copyright (c) 2015-2023 LG Electronics, Inc.

SUMMARY = "Settings Service Configs"
AUTHOR = "Rajesh Gopu I.V <rajeshgopu.iv@lge.com>"
SECTION = "webos/base"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

WEBOS_VERSION = "1.0.0-10_096b9fc2fe6fbedcda54a2ccaef1770381f2e5ee"
PR = "r1"

inherit webos_component
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_public_repo

WEBOS_REPO_NAME = "webos-settingsservice-conf"
SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"

S = "${WORKDIR}/git"
