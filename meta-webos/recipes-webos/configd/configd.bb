# Copyright (c) 2014-2021 LG Electronics, Inc.

SUMMARY = "webOS Configuration Service"
AUTHOR  = "Sangwoo Kang <sangwoo82.kang@lge.com>"
SECTION = "webos/base"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=89aea4e17d99a7cacdbeed46a0096b10 \
                    file://oss-pkg-info.yaml;md5=2bdfe040dcf81b4038370ae96036c519 \
"

DEPENDS = "luna-service2 pmloglib glib-2.0 libpbnjson gtest"
RDEPENDS_${PN} += "configd-data"

WEBOS_VERSION = "1.2.0-14_0c8c61a3285f11e8e285a33d71280964967fa019"
PR = "r18"

inherit webos_component
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_system_bus
inherit webos_daemon
inherit webos_machine_dep
inherit webos_distro_dep
inherit webos_distro_variant_dep
inherit webos_public_repo

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

PACKAGES =+ "${PN}-tests"
FILES_${PN}-tests = "${libexecdir}/tests/*"
FILES_${PN} += "${webos_sysbus_datadir}"
