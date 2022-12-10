# Copyright (c) 2022 LG Electronics, Inc.

SUMMARY = "webOS AI Framework Auto Acceleration Library"
DESCRIPTION = "webOS AI Framework Auto Delegate Selector and Acceleration Policy Manager for TFLite"
AUTHOR = "Ki-Joong Lee <kijoong.lee@lge.com>"
SECTION = "libs"
LICENSE = "CLOSED & Apache-2.0"
LIC_FILES_CHKSUM += "file://oss-pkg-info.yaml;md5=6567ba3096db0a2f26519bac18dfec8a"

WEBOS_VERSION = "1.0.0-22_95c6053d244fc8d409563ba42776aa6e33482334"
PR = "r0"

inherit webos_component
inherit webos_enhanced_submissions
inherit webos_cmake
inherit pkgconfig
inherit webos_filesystem_paths
inherit webos_test_provider
inherit webos_public_repo

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"

S = "${WORKDIR}/git"

DEPENDS = " \
    flatbuffers \
    tensorflow-lite \
    rapidjson \
    googletest \
    pmloglib \
"

AIF_INSTALL_DIR = "${datadir}/aif"
AIF_INSTALL_TEST_DIR = "${AIF_INSTALL_DIR}/test"

EXTRA_OECMAKE += "-DAIF_INSTALL_DIR=${AIF_INSTALL_DIR}"
EXTRA_OECMAKE += "-DAIF_INSTALL_TEST_DIR=${AIF_INSTALL_TEST_DIR}"

PACKAGES =+ "${PN}-tests"
FILES:${PN}-tests += "${AIF_INSTALL_TEST_DIR}"

INSANE_SKIP:${PN} = "dev-so"

FILES_SOLIBSDEV = ""
FILES:${PN} += " \
    ${libdir}/*.so \
"
