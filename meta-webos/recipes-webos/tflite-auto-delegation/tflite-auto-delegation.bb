# Copyright (c) 2022-2023 LG Electronics, Inc.

SUMMARY = "webOS AI Framework Auto Acceleration Library"
DESCRIPTION = "webOS AI Framework Auto Delegate Selector and Acceleration Policy Manager for TFLite"
AUTHOR = "Ki-Joong Lee <kijoong.lee@lge.com>"
SECTION = "libs"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=86d3f3a95c324c9479bd8986968f4327"

WEBOS_VERSION = "1.0.0-30_fc288ece6e918c1b531dc1c7fd3470283c3ce76e"
PR = "r3"

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

PACKAGECONFIG += "${@bb.utils.contains('MACHINE_FEATURES', 'gl-backend', 'gl-backend', '', d)}"
PACKAGECONFIG += "${@bb.utils.contains('COMBINED_FEATURES', 'edgetpu', 'edgetpu', '', d)}"
PACKAGECONFIG += "${@bb.utils.contains('COMBINED_FEATURES', 'npu-delegate', 'npu', '', d)}"

PACKAGECONFIG[edgetpu] = "-DWITH_EDGETPU:BOOL=TRUE,-DWITH_EDGETPU:BOOL=FALSE,libedgetpu"
PACKAGECONFIG[gl-backend] = "-DTFLITE_ENABLE_GPU_GL_ONLY=ON, -DTFLITE_ENABLE_GPU_GL_ONLY=OFF, virtual/egl virtual/libgles2"
PACKAGECONFIG[npu] = "-DWITH_NPU=ON,-DWITH_NPU=OFF,tflite-npu-delegate"

# There is gl-backend PACKAGECONFIG which respects gpu-delegate in DISTRO_FEATURES, but still fails to build without gpu-delegate
# http://gecko.lge.com:8000/Errors/Details/582724
# FAILED: test/auto_delegation_test
# TOPDIR/BUILD/work/mach-distro-linux-gnueabi/tflite-auto-delegation/1.0.0-27-r1/..../11.3.0/ld: auto_delegation/libauto-delegation.so.1.0.0: undefined reference to `TfLiteGpuDelegateOptionsV2Default'
# TOPDIR/BUILD/work/mach-distro-linux-gnueabi/tflite-auto-delegation/1.0.0-27-r1/..../11.3.0/ld: auto_delegation/libauto-delegation.so.1.0.0: undefined reference to `TfLiteGpuDelegateV2Create'
inherit features_check
REQUIRED_DISTRO_FEATURES = "gpu-delegate"

EXTRA_OECMAKE += "-DAIF_INSTALL_DIR=${AIF_INSTALL_DIR}"
EXTRA_OECMAKE += "-DAIF_INSTALL_TEST_DIR=${AIF_INSTALL_TEST_DIR}"

PACKAGES =+ "${PN}-tests"
FILES:${PN}-tests += "${AIF_INSTALL_TEST_DIR}"
