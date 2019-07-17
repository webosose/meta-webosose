# Copyright (c) 2019 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos1"

IMAGE_CMD_cpio_append_sota() {
    # meta-updater and webos.inc uses different image name, need to match it
    if [ ! -f ${IMGDEPLOYDIR}/${INITRAMFS_IMAGE}-${MACHINE}.${INITRAMFS_FSTYPES} ]; then
        ln -sf ./${IMAGE_NAME}${IMAGE_NAME_SUFFIX}.${INITRAMFS_FSTYPES} \
            ${IMGDEPLOYDIR}/${INITRAMFS_IMAGE}-${MACHINE}.${INITRAMFS_FSTYPES}
    fi
}
