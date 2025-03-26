# Copyright (c) 2023-2025 LG Electronics, Inc.

DESCRIPTION = "Components for camera added to webOS"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

PR = "r3"

inherit packagegroup
inherit features_check

REQUIRED_DISTRO_FEATURES = "webos-camera"

RDEPENDS:${PN} = " \
    g-camera-pipeline \
    packagegroup-webos-media \
"
