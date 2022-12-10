# Copyright (c) 2022 LG Electronics, Inc.

DESCRIPTION = "Machine learning components used in webOS"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

# You don't need to change this value when you're changing just RDEPENDS:${PN} variable.
PR = "r0"

PACKAGE_ARCH = "${MACHINE_ARCH}"
inherit packagegroup
inherit webos_machine_impl_dep
inherit webos_prerelease_dep

USE_ARMNN = " \
    arm-compute-library \
    armnn \
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
    ${@bb.utils.contains('DISTRO_FEATURES', 'armnn', '${USE_ARMNN}', '', d)} \
"

RDEPENDS:${PN} = " \
    ${AIFRAMEWORK_CORE} \
    ${AIFRAMEWORK_EXTENDED} \
"
