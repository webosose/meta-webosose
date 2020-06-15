# Copyright (c) 2017-2020 LG Electronics, Inc.

SUMMARY = "Input Device Creator Daemon"
AUTHOR = "Jikyun Byun <jk.byun@lgepartner.com>"
SECTION = "webos/tools"
LICENSE = "CLOSED"

WEBOS_VERSION = "2.0.0-14_4f64149ed2f44b88ec3f83abf88fe4977020a305"

PR = "r5"

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
