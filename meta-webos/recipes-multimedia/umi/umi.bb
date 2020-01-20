# Copyright (c) 2018-2020 LG Electronics, Inc.

SUMMARY = "AudioOutputd adaptation layer (UMI) API definition and test harness"
SECTION = "webos/libs"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://CMakeLists.txt;beginline=1;endline=15;md5=059bf74645cdef24f5e9a0ccb2a4cb94"

inherit webos_component
inherit webos_enhanced_submissions
inherit webos_pkgconfig
inherit webos_cmake
inherit webos_library
inherit webos_test_provider
inherit webos_public_repo

DEPENDS = "glib-2.0 pmloglib libpbnjson alsa-lib"

WEBOS_VERSION = "1.0.0-6_555a0cc7812bbe675cbcf1f18c5ca20da2c9a285"
PR = "r2"

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

EXTRA_OECMAKE_append_qemux86 = " -DWEBOS_SOC:STRING=qemux86"
