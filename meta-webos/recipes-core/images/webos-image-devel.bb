# Copyright (c) 2012-2025 LG Electronics, Inc.

SUMMARY = "webOS OSE developer/test image"
DESCRIPTION = "This image contains additional packages useful during \
development of webOS OSE. It also all of the additional packages needed for \
testing webOS OSE, including all unit and integration tests, and those \
required to support automated testing."
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

PR = "r3${IMAGE_PR_SUFFIX}"

IMAGE_FEATURES += "${WEBOS_IMAGE_DEFAULT_FEATURES}"
IMAGE_FEATURES += "${WEBOS_IMAGE_DEFAULT_SSH_IMAGE_FEATURE}"

IMAGE_FEATURES += "tools-debug tools-profile debug-tweaks"
IMAGE_FEATURES += "webos-devel"

IMAGE_FEATURES += "webos-test"

inherit webos_image

IMAGE_ROOTFS_EXTRA_SPACE = "524288"
