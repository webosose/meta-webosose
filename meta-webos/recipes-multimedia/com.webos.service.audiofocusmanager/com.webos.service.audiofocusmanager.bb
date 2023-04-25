# Copyright (c) 2021-2023 LG Electronics, Inc.

DESCRIPTION = "webOS audiofocusmanager"
AUTHOR = "Sushovan G <sushovan.g@lge.com>"
SECTION = "webos/base"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10 \
    file://oss-pkg-info.yaml;md5=2bdfe040dcf81b4038370ae96036c519 \
"

DEPENDS = "glib-2.0 libpbnjson luna-service2 pmloglib"

WEBOS_VERSION = "1.0.0-6_bf116ace9f497f2be05ce52f9a0e377b56c62376"
PR = "r1"

inherit webos_component
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_library
inherit webos_daemon
inherit webos_system_bus
inherit webos_machine_dep
inherit gettext
inherit webos_lttng
inherit webos_public_repo
inherit webos_distro_dep
inherit webos_machine_impl_dep

# [http://gpro.lge.com/c/webosose/com.webos.service.audiofocusmanager/+/348165 Fix luna-service2 usage]
SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE} \
    file://0001-Fix-luna-service2-usage.patch \
"
S = "${WORKDIR}/git"

