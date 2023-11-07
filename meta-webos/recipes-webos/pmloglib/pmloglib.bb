# Copyright (c) 2012-2023 LG Electronics, Inc.

SUMMARY = "webOS logging library"
AUTHOR = "Sukil Hong <sukil.hong@lge.com>"
SECTION = "webos/libs"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10 \
    file://oss-pkg-info.yaml;md5=2bdfe040dcf81b4038370ae96036c519 \
"

DEPENDS = "glib-2.0 libpbnjson"

WEBOS_VERSION = "3.3.0-9_dde40f333009e150b455468994ceb46a8cbf60ea"
PR = "r10"

LEAD_SONAME = "libPmLogLib.so"
EXTRA_OECMAKE += "-DWEBOS_DISTRO_PRERELEASE:STRING='${WEBOS_DISTRO_PRERELEASE}'"

inherit webos_component
inherit webos_public_repo
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_library
inherit webos_prerelease_dep
inherit webos_pmlog_config

PACKAGECONFIG ??= ""
PACKAGECONFIG[whitelist] = "-DENABLE_WHITELIST:BOOL=TRUE, -DENABLE_WHITELIST:BOOL=FALSE"

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"
