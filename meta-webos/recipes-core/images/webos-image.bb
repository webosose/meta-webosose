# Copyright (c) 2012-2018 LG Electronics, Inc.

DESCRIPTION = "webOS OSE image"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

PR = "r3"

IMAGE_FEATURES += "${WEBOS_IMAGE_DEFAULT_FEATURES}"

IMAGE_FEATURES += "${@'' if '${WEBOS_DISTRO_PRERELEASE}' == '' else 'debug-tweaks'}"

inherit webos_image

IMAGE_ROOTFS_EXTRA_SPACE = "524288"
