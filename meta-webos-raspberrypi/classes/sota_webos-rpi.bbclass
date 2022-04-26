# Copyright (c) 2019-2022 LG Electronics, Inc.

# Rather than using sota_raspberrypi.bbclass on meta-updater/classes,
# this setting keeps the boot partition then original one as possible.
#
# Differences (between sota_raspberrypi.bbclass)
#  - Don't use 'fitImage' kernel type for '/boot/ostree/webos-{hash}/vmlinuz'.
#  - So, need to use initramfs on the u-boot directly. (cpio.gz => cpio.gz.u-boot)
#  - Copy DTBs, DTBOs to 1st partition (like original rpi image)

INITRAMFS_FSTYPES = "cpio.gz.u-boot"
OSTREE_KERNEL = "${KERNEL_IMAGETYPE}"
OSTREE_KERNEL_ARGS:sota = ""

# enable u-boot and set variables
RPI_USE_U_BOOT:sota = "1"
PREFERRED_PROVIDER_virtual/bootloader_sota ?= "u-boot"
OSTREE_BOOTLOADER = "u-boot"
UBOOT_ENTRYPOINT:sota = "0x00008000"
SOTA_CLIENT_FEATURES:append = " ubootenv"

# wic image used by ostree
IMAGE_FSTYPES:remove:sota = "rpi-sdimg"

# kernel image handled by ostree
IMAGE_BOOT_FILES:remove:sota = "${KERNEL_IMAGETYPE}"
