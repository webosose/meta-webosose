# Copyright (c) 2022-2023 LG Electronics, Inc.

SUMMARY = "webOS Edge AI Computer Vision Library"
DESCRIPTION = "webOS Edge AI Computer Vision Library using TensorflowLite"
AUTHOR = "Ki-Joong Lee <kijoong.lee@lge.com>"
SECTION = "libs"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://LICENSE;md5=86d3f3a95c324c9479bd8986968f4327 \
    file://oss-pkg-info.yaml;md5=6e87e90c168c712da8accb5afc402bf4\
"

WEBOS_VERSION = "1.0.0-45_728b5170cff02a99ae8459a0031f662f9e18a26e"
WEBOS_REPO_NAME = "edge-ai-computer-vision"
SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"

PR = "r9"
S = "${WORKDIR}/git"

inherit cmake
inherit pkgconfig
inherit webos_filesystem_paths
inherit webos_enhanced_submissions
inherit webos_public_repo
inherit webos_test_provider
inherit features_check

REQUIRED_DISTRO_FEATURES = "webos-aiframework"

DEPENDS = " \
    msgpack-cpp \
    rapidjson \
    flatbuffers \
    opencv \
    tensorflow-lite \
    googletest \
    pmloglib \
"

AIF_INSTALL_DIR = "${datadir}/aif"
AIF_INSTALL_TEST_DIR = "${AIF_INSTALL_DIR}/test"
AIF_INSTALL_EXAMPLE_DIR = "${AIF_INSTALL_DIR}/example"

EXTRA_OECMAKE += "-DAIF_INSTALL_DIR=${AIF_INSTALL_DIR}"
EXTRA_OECMAKE += "-DAIF_INSTALL_TEST_DIR=${AIF_INSTALL_TEST_DIR}"
EXTRA_OECMAKE += "-DAIF_INSTALL_EXAMPLE_DIR=${AIF_INSTALL_EXAMPLE_DIR}"
EXTRA_OECMAKE += "-DWITH_UPDATABLE_MODELS=OFF"

PACKAGECONFIG ?= "xnnpack"
PACKAGECONFIG += "${@bb.utils.contains('COMBINED_FEATURES', 'gpu-delegate', 'gpu', '', d)}"
PACKAGECONFIG += "${@bb.utils.contains('COMBINED_FEATURES', 'edgetpu', 'edgetpu', '', d)}"
PACKAGECONFIG += "${@bb.utils.contains('MACHINE_FEATURES', 'armnn', 'armnn', '', d)}"
PACKAGECONFIG += "${@bb.utils.contains('DISTRO_FEATURES', 'ml-library-size-reduction', '', 'examples', d)}"
PACKAGECONFIG += "${@bb.utils.contains('COMBINED_FEATURES', 'auto-acceleration', 'ads', '', d)}"
PACKAGECONFIG += "${@bb.utils.contains('MACHINE_FEATURES', 'npu-delegate', 'npu xtensor fittv', '', d)}"
PACKAGECONFIG += "${@bb.utils.contains('MACHINE_FEATURES', 'nnapi', 'nnapi', '', d)}"

PACKAGECONFIG[xnnpack] = "-DWITH_XNNPACK:BOOL=TRUE,-DWITH_XNNPACK:BOOL=FALSE"
PACKAGECONFIG[gpu] = "-DWITH_GPU=ON, -DWITH_GPU=OFF"
PACKAGECONFIG[edgetpu] = "-DWITH_EDGETPU:BOOL=TRUE,-DWITH_EDGETPU:BOOL=FALSE,libedgetpu"
PACKAGECONFIG[armnn] = "-DWITH_ARMNN:BOOL=TRUE,-DWITH_ARMNN:BOOL=FALSE,armnn"
PACKAGECONFIG[examples] = "-DWITH_EXAMPLES=ON -DWITH_EXTRA_MODELS=ON,-DWITH_EXAMPLES=OFF -DWITH_EXTRA_MODELS=OFF,,"
PACKAGECONFIG[ads] = "-DWITH_AUTO_DELEGATE=ON,-DWITH_AUTO_DELEGATE=OFF,tflite-auto-delegation"
PACKAGECONFIG[npu] = "-DWITH_NPU=ON,-DWITH_NPU=OFF,tflite-npu-delegate"
PACKAGECONFIG[xtensor] = "-DWITH_XTENSOR:BOOL=TRUE,-DWITH_XTENSOR:BOOL=FALSE,xtensor xtl xsimd"
PACKAGECONFIG[fittv] = "-DWITH_FITTV:BOOL=TRUE,-DWITH_FITTV:BOOL=FALSE"
PACKAGECONFIG[nnapi] = "-DWITH_NNAPI=ON,-DWITH_NNAPI=OFF"

PACKAGES =+ "${PN}-tests"

FILES:${PN}-tests = " \
    ${AIF_INSTALL_TEST_DIR} \
    ${AIF_INSTALL_TEST_DIR}/test-dl \
    ${AIF_INSTALL_DIR}/images \
"

FILES:${PN} += " \
    ${AIF_INSTALL_DIR} \
"
