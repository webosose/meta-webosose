# Copyright (c) 2019-2021 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos2"

IMAGE_CMD:cpio:append:sota() {
    # meta-updater and webos.inc uses different image name, need to match it
    if [ ! -f ${IMGDEPLOYDIR}/${INITRAMFS_IMAGE}-${MACHINE}.${INITRAMFS_FSTYPES} ]; then
        ln -sf ./${IMAGE_NAME}${IMAGE_NAME_SUFFIX}.${INITRAMFS_FSTYPES} \
            ${IMGDEPLOYDIR}/${INITRAMFS_IMAGE}-${MACHINE}.${INITRAMFS_FSTYPES}
    fi
}

# Fix build error (bitbake --runall patch webos-image)
# ERROR: An uncaught exception occurred in runqueue
# KeyError: '/home/builder/output/build-webos/meta-updater/recipes-core/images/initramfs-ostree-image.bb:do_unpack'
deltask do_unpack
