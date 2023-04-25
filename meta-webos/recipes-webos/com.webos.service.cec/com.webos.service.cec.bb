# Copyright (c) 2022-2023 LG Electronics, Inc.

SUMMARY = "HDMI CEC service for webOS OSE"
AUTHOR = "Rajesh Gopu I.V <rajeshgopu.iv@lge.com>"
SECTION = "webosose"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://LICENSE;md5=89aea4e17d99a7cacdbeed46a0096b10 \
    file://oss-pkg-info.yaml;md5=2bdfe040dcf81b4038370ae96036c519 \
"

DEPENDS = "glib-2.0 libpbnjson luna-service2 pmloglib nyx-lib"

COMPATIBLE_MACHINE = "^raspberrypi4-64$"

WEBOS_VERSION = "1.0.0-5_a2236e7706698cdd091c64e4bafe49c8eac635ab"
PR = "r1"

inherit webos_component
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_daemon
inherit webos_system_bus
inherit webos_public_repo

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"
