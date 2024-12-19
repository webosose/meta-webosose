# Copyright (c) 2022-2024 LG Electronics, Inc.

SUMMARY = "webOS Edge AI Computer Vision Library"
DESCRIPTION = "webOS Edge AI Computer Vision Library using TensorflowLite"
AUTHOR = "Ki-Joong Lee <kijoong.lee@lge.com>"
SECTION = "libs"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://LICENSE;md5=d46cc1e419bce84e1021fb5426e4363a \
    file://oss-pkg-info.yaml;md5=b175693811514d0ad08577aa0fc5f16a\
"

WEBOS_VERSION = "1.1.0-85_7c5e9a1ebbb294bb7e0e564299820be94fea52e7"
WEBOS_REPO_NAME = "edge-ai-computer-vision"
SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"

PR = "r6"
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
    flatbuffers \
    googletest \
    msgpack-cpp \
    nlohmann-json \
    opencv \
    pmloglib \
    rapidjson \
    tensorflow-lite \
    xsimd \
    xtensor \
    xtl \
"

AIF_INSTALL_DIR = "${datadir}/aif"
AIF_INSTALL_TEST_DIR = "${AIF_INSTALL_DIR}/test"
AIF_INSTALL_EXAMPLE_DIR = "${AIF_INSTALL_DIR}/example"
AIF_ALLOWED_EXTENSIONS = "libedgeai-vision-base"
AIF_EXTENSION_REGISTRY_PATH = "/tmp/edgeai_extension_registry.json"

EXTRA_OECMAKE += "-DAIF_INSTALL_DIR=${AIF_INSTALL_DIR}"
EXTRA_OECMAKE += "-DAIF_INSTALL_TEST_DIR=${AIF_INSTALL_TEST_DIR}"
EXTRA_OECMAKE += "-DAIF_INSTALL_EXAMPLE_DIR=${AIF_INSTALL_EXAMPLE_DIR}"
EXTRA_OECMAKE += "-DWITH_UPDATABLE_MODELS=OFF"
EXTRA_OECMAKE += "-DAIF_ALLOWED_EXTENSIONS=${AIF_ALLOWED_EXTENSIONS}"
EXTRA_OECMAKE += "-DAIF_EXTENSION_REGISTRY_PATH=${AIF_EXTENSION_REGISTRY_PATH}"

PACKAGECONFIG ?= "xnnpack"
PACKAGECONFIG += "${@bb.utils.contains('COMBINED_FEATURES', 'gpu-delegate', 'gpu', '', d)}"
PACKAGECONFIG += "${@bb.utils.contains('COMBINED_FEATURES', 'webos-edgetpu', 'edgetpu', '', d)}"
PACKAGECONFIG += "${@bb.utils.contains('MACHINE_FEATURES', 'webos-armnn', 'armnn', '', d)}"
PACKAGECONFIG += "${@bb.utils.contains('COMBINED_FEATURES', 'webos-auto-acceleration', 'ads', '', d)}"
PACKAGECONFIG += "${@bb.utils.contains('COMBINED_FEATURES', 'npu-delegate', 'npu', '', d)}"
PACKAGECONFIG += "${@bb.utils.contains('COMBINED_FEATURES', 'nnapi', 'nnapi', '', d)}"

PACKAGECONFIG[xnnpack] = "-DWITH_XNNPACK:BOOL=TRUE,-DWITH_XNNPACK:BOOL=FALSE"
PACKAGECONFIG[gpu] = "-DWITH_GPU=ON, -DWITH_GPU=OFF"
PACKAGECONFIG[edgetpu] = "-DWITH_EDGETPU:BOOL=TRUE,-DWITH_EDGETPU:BOOL=FALSE,libedgetpu"
PACKAGECONFIG[armnn] = "-DWITH_ARMNN:BOOL=TRUE,-DWITH_ARMNN:BOOL=FALSE,armnn"
PACKAGECONFIG[ads] = "-DWITH_AUTO_DELEGATE=ON,-DWITH_AUTO_DELEGATE=OFF,tflite-auto-delegation"
PACKAGECONFIG[npu] = "-DWITH_NPU=ON,-DWITH_NPU=OFF,tflite-npu-delegate"
PACKAGECONFIG[nnapi] = "-DWITH_NNAPI=ON,-DWITH_NNAPI=OFF"

# =+ means prepends. so don't change the order, extra should be run before tests.
PACKAGES =+ "${PN}-tests"
PACKAGES =+ "${PN}-extra"
PACKAGES =+ "${PN}-examples"

FILES:${PN}-examples = " \
    ${AIF_INSTALL_EXAMPLE_DIR} \
    ${libdir}/libedgeai-example-tools.so.* \
"

FILES:${PN}-extra = " \
    ${AIF_INSTALL_TEST_DIR}/edgeai-vision-test-extra* \
    ${AIF_INSTALL_DIR}/extra_models \
"

FILES:${PN}-tests = " \
    ${AIF_INSTALL_TEST_DIR} \
    ${AIF_INSTALL_DIR}/images \
"

FILES:${PN} += " \
    ${AIF_INSTALL_DIR} \
    ${libdir}/edgeai-extensions \
"

FILES:${PN}-dev += " \
    ${libdir}/edgeai-extensions/*.so \
"

RDEPENDS:${PN}-examples += " ${PN}-extra"
