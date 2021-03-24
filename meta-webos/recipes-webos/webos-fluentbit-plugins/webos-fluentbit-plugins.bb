# Copyright (c) 2021 LG Electronics, Inc.

SUMMARY = "webOS fluentbit plugins"
AUTHOR = "Hotaek Jung <hotaek.jung@lge.com>"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10 \
                    file://oss-pkg-info.yaml;md5=2bdfe040dcf81b4038370ae96036c519 \
"

DEPENDS = "glib-2.0 luna-service2 json-c libpbnjson fluentbit"

WEBOS_VERSION = "1.0.0-1_e386cd0c3bf93bedca2b7a7dab265078dd091fca"
PR = "r0"

inherit webos_component
inherit webos_enhanced_submissions
inherit webos_pkgconfig
inherit webos_cmake
inherit webos_system_bus
inherit webos_public_repo

SRC_URI="${WEBOSOSE_GIT_REPO_COMPLETE}"

S = "${WORKDIR}/git"
