# Copyright (c) 2015-2020 LG Electronics, Inc.

SUMMARY = "Plugin for Qt applications that communicates with telluriumd"
AUTHOR  = "Volodymyr Lushpenko <volodymyr.lushpenko@lge.com>"
SECTION = "webos/tools"
LICENSE = "CLOSED"

DEPENDS = "glibmm luna-service2 libpbnjson pmloglib qtdeclarative"

WEBOS_VERSION = "1.0.1-22_ceee6cd85843ce887e95a0ea77bd99eeb30678fa"
PR = "r2"

inherit webos_cmake
inherit webos_component
inherit webos_program
inherit webos_enhanced_submissions

SRC_URI = "${WEBOS_PRO_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"
FILES_${PN} += "${libdir}/libqttestability.so"
FILES_${PN}-dev = ""
