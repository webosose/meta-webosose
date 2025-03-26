# Copyright (c) 2015-2025 LG Electronics, Inc.

SUMMARY = "Settings Service Configs"
AUTHOR = "Rajesh Gopu I.V <rajeshgopu.iv@lge.com>"
SECTION = "webos/base"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

WEBOS_VERSION = "1.0.0-11_8e3694a3d3da9a26a688bda58655d13e714c3967"
PR = "r2"

inherit webos_component
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_public_repo

WEBOS_REPO_NAME = "webos-settingsservice-conf"
SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"

S = "${WORKDIR}/git"
