# Copyright (c) 2020 LG Electronics, Inc

DESCRIPTION = "webOS IoT image"
LICENSE = "CLOSED"

PR = "r0"

IMAGE_FEATURES += "${WEBOS_IMAGE_DEFAULT_FEATURES}"
IMAGE_FEATURES += "${@'' if '${WEBOS_DISTRO_PRERELEASE}' == '' else 'debug-tweaks'}"

inherit webos_iot

IMAGE_ROOTFS_EXTRA_SPACE = "16384"
