# Copyright (c) 2012-2018 LG Electronics, Inc.

SUMMARY = "webOS OSE developer/test image & SDK"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

PR = "r1"

IMAGE_FEATURES += "${WEBOS_IMAGE_DEFAULT_FEATURES}"
IMAGE_FEATURES += "${WEBOS_IMAGE_DEFAULT_SSH_IMAGE_FEATURE}"

IMAGE_FEATURES += "tools-debug tools-profile debug-tweaks"
IMAGE_FEATURES += "webos-devel"

IMAGE_FEATURES += "ptest-pkgs"
IMAGE_FEATURES += "webos-test"

inherit webos_image
inherit populate_sdk populate_sdk_qt5

IMAGE_ROOTFS_EXTRA_SPACE = "524288"

