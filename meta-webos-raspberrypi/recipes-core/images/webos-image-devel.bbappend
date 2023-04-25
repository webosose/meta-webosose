# Copyright (c) 2023 LG Electronics, Inc.

EXTENDPRAUTO:append:rpi = "webosrpi2"

# The rootfs will be mounted as R/O and R/W parttions are mounte.
# Or, resize-rootfs will expand rootfs, so we don't need extra space a lot.
IMAGE_ROOTFS_EXTRA_SPACE = "16384"
IMAGE_OVERHEAD_FACTOR = "1.1"
IMAGE_ROOTFS_SIZE = "4032985"
