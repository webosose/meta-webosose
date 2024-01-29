# Copyright (c) 2017-2024 LG Electronics, Inc.

SUMMARY = "Luna service C++11 helpers library"
AUTHOR = "Yogish S <yogish.s@lge.com>"
SECTION = "webos/services"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=89aea4e17d99a7cacdbeed46a0096b10 \
    file://oss-pkg-info.yaml;md5=d20efabe0484dde1cf977f3ea79f4d14 \
"

DEPENDS = "glib-2.0 luna-service2 pmloglib libpbnjson"

WEBOS_VERSION = "1.0.0-3_4f87d6defcd379e65e1397f9d0ead1e1697756b6"
PR = "r5"

inherit webos_component
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_library
inherit webos_test_provider
inherit webos_public_repo

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE} \
    file://0006-Fix-coverity-issue-9099028.patch \
"
S = "${WORKDIR}/git"
