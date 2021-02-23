# Copyright (c) 2020-2021 LG Electronics, Inc.

DESCRIPTION = "Utility library used in Location Framework"
AUTHOR = "vibhanshu.dhote <vibhanshu.dhote@lge.com>"
SECTION = "webos/location"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10 \
    file://oss-pkg-info.yaml;md5=71729d222d4be4c1e2518bb8770abeee \
"

DEPENDS = "glib-2.0 curl pmloglib"

WEBOS_VERSION = "1.0.0-18_8aeb2c1f60e58731dd73b701d6f9dcce1d094a44"
PR = "r2"

inherit webos_component
inherit webos_public_repo
inherit webos_enhanced_submissions
inherit webos_cmake

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"
