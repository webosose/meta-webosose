# Copyright (c) 2022-2024 LG Electronics, Inc.

DESCRIPTION = "Machine learning components used in webOS"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

PR = "r4"

PACKAGE_ARCH = "${MACHINE_ARCH}"
inherit packagegroup
inherit webos_machine_impl_dep
inherit webos_prerelease_dep
inherit features_check

REQUIRED_DISTRO_FEATURES = "webos-aiframework"

USE_ARMNN = " \
    arm-compute-library \
    armnn \
"

USE_EDGETPU = " \
    libedgetpu \
"

AIFRAMEWORK_CORE = " \
    benchmark-model \
    edgeai-vision \
    edgeai-vision-tests \
    flatbuffers \
    opencl-icd-loader \
    opencv \
    tensorflow-lite \
    xnnpack \
"

AIFRAMEWORK_EXTENDED = " \
    ${@bb.utils.contains('MACHINE_FEATURES', 'webos-armnn', '${USE_ARMNN}', '', d)} \
    ${@bb.utils.contains('COMBINED_FEATURES', 'webos-edgetpu', '${USE_EDGETPU}', '', d)} \
"

RDEPENDS:${PN} = " \
    ${AIFRAMEWORK_CORE} \
    ${AIFRAMEWORK_EXTENDED} \
"
