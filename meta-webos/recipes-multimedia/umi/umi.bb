# Copyright (c) 2018-2020 LG Electronics, Inc.

SUMMARY = "AudioOutputd adaptation layer (UMI) API definition and test harness"
SECTION = "webos/libs"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
file://CMakeLists.txt;beginline=1;endline=15;md5=059bf74645cdef24f5e9a0ccb2a4cb94 \
file://oss-pkg-info.yaml;md5=2bdfe040dcf81b4038370ae96036c519 \
"

inherit webos_component
inherit webos_enhanced_submissions
inherit webos_pkgconfig
inherit webos_cmake
inherit webos_library
inherit webos_test_provider
inherit webos_public_repo

DEPENDS = "glib-2.0 pmloglib libpbnjson alsa-lib"

WEBOS_VERSION = "1.0.0-9_d85b792873c4d239c2b9351de2efb85486a6237b"
PR = "r3"

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

EXTRA_OECMAKE_append_qemux86 = " -DWEBOS_SOC:STRING=qemux86"
