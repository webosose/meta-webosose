# Copyright (c) 2015-2017 LG Electronics, Inc.
#
# packagegroup
#
# Intercept the upstream kernel.bbclass so that when there's a webos_deploy_fixup
# task.
#

require ${COREBASE}/meta/classes/kernel.bbclass

inherit webos_deploy

do_deploy_append() {
    # The .bin-s are of no use to us.
    for type in ${KERNEL_IMAGETYPES} ; do
        rm -vf ${DEPLOYDIR}/${type}-${KERNEL_IMAGE_SYMLINK_NAME}.bin
        rm -vf ${DEPLOYDIR}/${type}
    done
}

do_webos_deploy_fixup_prepend() {
    [ -e       ${DEPLOY_DIR_IMAGE}/${KERNEL_IMAGE_BASE_NAME}.bin ] && \
        ln -vf ${DEPLOY_DIR_IMAGE}/${KERNEL_IMAGE_BASE_NAME}.bin \
               ${DEPLOY_DIR_IMAGE}/${KERNEL_IMAGE_SYMLINK_NAME}.bin
    [ -e       ${DEPLOY_DIR_IMAGE}/${KERNEL_IMAGETYPE}-${KERNEL_IMAGE_BASE_NAME}.bin ] && \
        ln -vf ${DEPLOY_DIR_IMAGE}/${KERNEL_IMAGETYPE}-${KERNEL_IMAGE_BASE_NAME}.bin \
               ${DEPLOY_DIR_IMAGE}/${KERNEL_IMAGETYPE}
    [ -e       ${DEPLOY_DIR_IMAGE}/${MODULE_IMAGE_BASE_NAME}.tgz ] && \
        ln -vf ${DEPLOY_DIR_IMAGE}/${MODULE_IMAGE_BASE_NAME}.bin \
               ${DEPLOY_DIR_IMAGE}/${MODULE_IMAGE_SYMLINK_NAME}.tgz
}
