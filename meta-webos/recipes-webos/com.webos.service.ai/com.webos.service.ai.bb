# Copyright (c) 2018-2021 LG Electronics, Inc.

SUMMARY = "Ai service for voice/face/gesture recognition"
AUTHOR = "Kyungjik Min <dp.min@lge.com>"
SECTION = "webos/extended-service"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57 \
    file://oss-pkg-info.yaml;md5=5e240f6c8ed6312f22c06e00a91dd340 \
"

DEPENDS = "glib-2.0 luna-service2 json-c pmloglib libgoogleassistant"

# libgoogleassistant contains prebuilt binaries useful only on raspberrypi3
COMPATIBLE_MACHINE = "^raspberrypi3$|^raspberrypi4$"
COMPATIBLE_MACHINE_raspberrypi3-64 = "^$"
COMPATIBLE_MACHINE_raspberrypi4-64 = "^$"

WEBOS_VERSION = "1.0.0-10_bca8a9b3669caae7173ea6196cdbfd86a88bb666"
PR = "r4"

inherit systemd
inherit webos_public_repo
inherit webos_component
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_system_bus
inherit webos_machine_impl_dep

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"
