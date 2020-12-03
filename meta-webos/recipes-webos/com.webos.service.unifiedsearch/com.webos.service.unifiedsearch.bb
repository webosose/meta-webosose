# Copyright (c) 2020 LG Electronics, Inc.

SUMMARY = "Unified search service"
AUTHOR = "Sangwoo Kang <sangwoo82.kang@lge.com>"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10 \
    file://oss-pkg-info.yaml;md5=2bdfe040dcf81b4038370ae96036c519 \
"

DEPENDS = "luna-service2 libpbnjson glib-2.0 procps sqlite3"

WEBOS_VERSION = "1.0.0-1_3839125533d2a477fa27301b4936af6500aae954"
PR = "r0"

inherit webos_component
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_public_repo
inherit webos_system_bus

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

PACKAGES += "${PN}-plugins"
FILES_${PN}-plugins = "/usr/lib/plugins"

EXTRA_OECMAKE += "-DUSE_BUILTIN_PLUGIN:bool=true"
