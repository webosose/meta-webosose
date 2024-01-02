# Copyright (c) 2023-2024 LG Electronics, Inc.

DESCRIPTION = "Components for camera added to webOS"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

PR = "r2"

inherit packagegroup
inherit features_check

REQUIRED_DISTRO_FEATURES = "webos-camera"

VIRTUAL-RUNTIME_g-camera-pipeline ?= ""
VIRTUAL-RUNTIME_g-camera-pipeline:rpi = "g-camera-pipeline"
VIRTUAL-RUNTIME_g-camera-pipeline:qemux86 = "g-camera-pipeline"
VIRTUAL-RUNTIME_g-camera-pipeline:qemux86-64 = "g-camera-pipeline"

RDEPENDS:${PN} = " \
    packagegroup-webos-media \
"

# Add to webos distro only
RDEPENDS:${PN}:append:webos = " \
    ${VIRTUAL-RUNTIME_g-camera-pipeline} \
"
