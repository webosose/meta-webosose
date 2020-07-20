# Copyright (c) 2014-2020 LG Electronics, Inc.

SUMMARY = "webOS Bluetooth SIL API"
AUTHOR = "Sameer Mulla <sameer.mulla@lge.com>"
SECTION = "webos/services"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10 \
    file://oss-pkg-info.yaml;md5=e07d738b032e6351a6608e81ea1b1b49 \
"

DEPENDS = "glib-2.0"

WEBOS_VERSION = "1.0.0-12_6ccd77e74b0dc0098a280df3e037a52faa5cc4e2"
PR = "r2"

inherit webos_component
inherit webos_public_repo
inherit webos_enhanced_submissions
inherit webos_cmake

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

EXTRA_OECMAKE += "-DWEBOS_CONFIG_BUILD_TESTS:BOOL=TRUE"

PACKAGES =. "${PN}-tests "
FILES_${PN}-tests = "${bindir}/bluetooth-sil-tester"

ALLOW_EMPTY_${PN} = "1"
