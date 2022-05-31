# Copyright (c) 2022 LG Electronics, Inc.

DESCRIPTION = "Machine learning dev components used in webOS"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

# You don't need to change this value when you're changing just RDEPENDS:${PN} variable.
PR = "r0"

PACKAGE_ARCH = "${MACHINE_ARCH}"
inherit packagegroup
inherit webos_machine_impl_dep
inherit webos_prerelease_dep

RDEPENDS:${PN} = " \
    flatbuffers-dev \
    tensorflow-lite-dev \
    opencl-icd-loader-dev \
    opencv-dev \
    msgpack-c-dev \
    rapidjson-dev \
    jsoncpp-dev \
    edgeai-vision-dev \
    ${@bb.utils.contains('DISTRO_FEATURES', 'armnn', 'arm-compute-library-dev', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'armnn', 'armnn-dev', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'edgetpu', 'libedgetpu-dev', '', d)} \
"

USE_WAYLAND = " \
    qtwayland-dev \
    qtwayland-plugins \
    qtwayland-tools \
"

RDEPENDS_${PN} += " \
    packagegroup-core-standalone-sdk-target \
    qtbase-dev \
    qtbase-plugins \
    qtbase-staticdev \
    qtbase-tools \
    qtdeclarative-dev \
    qtdeclarative-tools \
    qtdeclarative-staticdev \
    qttools-dev \
    qttools-tools \
    ${@bb.utils.contains('DISTRO_FEATURES', 'wayland', '${USE_WAYLAND}', '', d)} \
    googletest-dev \
"
