# Copyright (c) 2011-2025 LG Electronics, Inc.

SUMMARY = "Kernel logging daemon"
AUTHOR = "Sukil Hong <sukil.hong@lge.com>"
SECTION = "webos/base"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://LICENSE;md5=89aea4e17d99a7cacdbeed46a0096b10 \
    file://oss-pkg-info.yaml;md5=2bdfe040dcf81b4038370ae96036c519 \
"

DEPENDS = "glib-2.0"

WEBOS_VERSION = "2.0.0-5_adeca60318e918637d86665f9dbc5a31f9dfe039"
PR = "r8"

inherit webos_component
inherit webos_public_repo
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_daemon

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"
inherit webos_systemd
WEBOS_SYSTEMD_SERVICE = "pm-klog-daemon.service"
