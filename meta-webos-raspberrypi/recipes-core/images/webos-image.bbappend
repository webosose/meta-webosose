# Copyright (c) 2019-2023 LG Electronics, Inc.

EXTENDPRAUTO:append:rpi = "webosrpi3"

# The rootfs will be mounted as R/O and R/W parttions are mounte.
# Or, resize-rootfs will expand rootfs, so we don't need extra space a lot.
IMAGE_ROOTFS_EXTRA_SPACE = "16384"
IMAGE_OVERHEAD_FACTOR = "1.1"
IMAGE_ROOTFS_SIZE = "2823090"
