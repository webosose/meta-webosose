# Copyright (c) 2021-2025 LG Electronics, Inc.

DESCRIPTION = "webOS audiofocusmanager"
AUTHOR = "Sushovan G <sushovan.g@lge.com>"
SECTION = "webos/base"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://LICENSE;md5=89aea4e17d99a7cacdbeed46a0096b10 \
    file://oss-pkg-info.yaml;md5=2bdfe040dcf81b4038370ae96036c519 \
"

DEPENDS = "glib-2.0 libpbnjson luna-service2 pmloglib"

WEBOS_VERSION = "1.0.0-10_87ac4905356be71d1e869b1760a5833883b733b6"
PR = "r5"

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
inherit webos_machine_impl_dep

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

inherit webos_systemd
WEBOS_SYSTEMD_SERVICE = "audiofocusmanager.service"
