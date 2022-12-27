# Copyright (c) 2020-2023 LG Electronics, Inc.

SUMMARY = "webOS IoT developer/test image"
DESCRIPTION = "This image contains additional packages useful during \
development of webOS IoT. It also all of the additional packages needed for \
testing webOS IoT, including all unit and integration tests, and those \
required to support automated testing."
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

PR = "r0"

DEBUG_MODE = "debug"

IMAGE_FEATURES += "webos-iot-devtools"

IMAGE_FEATURES += "tools-debug tools-profile debug-tweaks"
IMAGE_FEATURES += "webos-devel"

IMAGE_FEATURES += "ptest-pkgs"
IMAGE_FEATURES += "webos-test"

inherit webos_iot_image

IMAGE_ROOTFS_EXTRA_SPACE = "16384"
