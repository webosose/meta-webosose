# Copyright (c) 2012-2021 LG Electronics, Inc.

SUMMARY = "An extensible object oriented component used to add service components to webOS"
AUTHOR = "Anatolii Sakhnik <anatolii.sakhnik@lge.com>"
SECTION = "webos/devel"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10 \
                    file://oss-pkg-info.yaml;md5=2bdfe040dcf81b4038370ae96036c519 \
"
DEPENDS = "librolegen glib-2.0 libpbnjson luna-service2"

WEBOS_VERSION = "2.0.0-3_799c30fc706728d5e0924502bdd0733f25090a2f"
PR = "r5"

inherit webos_component
inherit webos_public_repo
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_pkgconfig

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

ALLOW_EMPTY_${PN} = "1"
