# Copyright (c) 2017-2020 LG Electronics, Inc.

SUMMARY = "Input Device Creator Daemon"
AUTHOR = "Jikyun Byun <jk.byun@lgepartner.com>"
SECTION = "webos/tools"
LICENSE = "CLOSED"

DEPENDS = "glib-2.0 luna-service2 pmloglib libpbnjson"

WEBOS_VERSION = "2.0.0-15_7c0a5ab2e4f92e0c445a523abc2798e228b716d6"

PR = "r6"

inherit webos_component
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_daemon
inherit webos_system_bus
inherit webos_machine_impl_dep
inherit webos_distro_dep

SRC_URI = "${WEBOS_PRO_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

COMPATIBLE_MACHINE = "^hardware$"
