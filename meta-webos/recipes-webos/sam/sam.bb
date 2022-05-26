# Copyright (c) 2013-2022 LG Electronics, Inc.

DESCRIPTION = "System Application Manager"
AUTHOR = "Sangwoo Kang <sangwoo82.kang@lge.com>"
SECTION = "webos/base"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=89aea4e17d99a7cacdbeed46a0096b10 \
                    file://oss-pkg-info.yaml;md5=2bdfe040dcf81b4038370ae96036c519 \
"

DEPENDS = "glib-2.0 luna-service2 libpbnjson boost icu pmloglib procps libwebosi18n"
RDEPENDS:${PN} = "ecryptfs-utils"
RDEPENDS:${PN} += "${VIRTUAL-RUNTIME_webos-customization}"

VIRTUAL-RUNTIME_webos-customization ?= ""

WEBOS_VERSION = "2.0.0-63_6204f493184cbbceaf4cb26ab2fa2e34263dd818"
PR = "r26"

inherit webos_component
inherit webos_cmake
inherit webos_enhanced_submissions
inherit webos_daemon
inherit webos_system_bus
inherit webos_distro_dep
inherit webos_distro_variant_dep
inherit webos_public_repo

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

PACKAGES =+ "${PN}-tests"
ALLOW_EMPTY:${PN}-tests = "1"
FILES:${PN}-tests = "${libexecdir}/tests/*"
