# Copyright (c) 2023 LG Electronics, Inc.

DESCRIPTION = "Components for bluetooth added to webOS"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

PR = "r0"

inherit packagegroup
inherit features_check

REQUIRED_DISTRO_FEATURES = "webos-bluetooth"

VIRTUAL-RUNTIME_bluetooth_service ?= ""

RDEPENDS:${PN} = " \
    ${VIRTUAL-RUNTIME_bluetooth_service} \
"

# Add to qemu target only as recommendations
RRECOMMENDS:${PN}:append:qemuall = " \
    kernel-module-bluetooth \
    kernel-module-btbcm \
    kernel-module-btintel \
    kernel-module-btusb \
"
