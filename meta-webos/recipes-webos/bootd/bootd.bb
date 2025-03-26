# Copyright (c) 2013-2025 LG Electronics, Inc.

SUMMARY = "Bootd single-shot launching service"
DESCRIPTION = "Bootd is a simplified upstart-like component. It provides automatic single-shot launching at boot time"
AUTHOR = "Sukil Hong <sukil.hong@lge.com>"
SECTION = "webos/base"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://LICENSE;md5=89aea4e17d99a7cacdbeed46a0096b10 \
    file://oss-pkg-info.yaml;md5=2bdfe040dcf81b4038370ae96036c519 \
"

DEPENDS = "luna-service2 libpbnjson pmloglib glib-2.0 boost"

WEBOS_VERSION = "2.0.0-25_dc4ebefbd8b8288fcc74c92eb30dab29f07aad67"
PR = "r21"

inherit webos_component
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_daemon
inherit webos_system_bus
inherit webos_lttng
inherit webos_public_repo
inherit webos_prerelease_dep

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

inherit webos_systemd
WEBOS_SYSTEMD_SERVICE = "bootd.service"

SRC_URI:append:qemux86 = " \
    file://0001-display-count-check-for-emulator.patch \
"

SRC_URI:append:qemux86-64 = " \
    file://0001-display-count-check-for-emulator.patch \
"
# LTTNG option
# WEBOS_LTTNG_ENABLED = "0"
EXTRA_OECMAKE += " ${@bb.utils.contains('WEBOS_LTTNG_ENABLED', '1', '-DWEBOS_LTTNG_ENABLED:BOOLEAN=True', '', d)}"
EXTRA_OECMAKE += "-DWEBOS_DISTRO_PRERELEASE:STRING='${WEBOS_DISTRO_PRERELEASE}'"
