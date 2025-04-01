# Copyright (c) 2015-2025 LG Electronics, Inc.
#
# packagegroup
#
# Intercept the upstream kernel.bbclass so that when there's a webos_deploy_fixup
# task.
#

require ${COREBASE}/meta/classes/kernel.bbclass

inherit webos_deploy

# FIXME: These will be dropped when below is upstreamed.
# https://lists.openembedded.org/g/openembedded-core/message/202309
RPROVIDES:${KERNEL_PACKAGE_NAME}-base:append = " ${KERNEL_PACKAGE_NAME}-base"
RPROVIDES:${KERNEL_PACKAGE_NAME}-image:append = " ${KERNEL_PACKAGE_NAME}-image"

do_webos_deploy_fixup:prepend() {
    [ -e       ${DEPLOY_DIR_IMAGE}/${KERNEL_IMAGE_NAME}.bin ] && \
        ln -vf ${DEPLOY_DIR_IMAGE}/${KERNEL_IMAGE_NAME}.bin \
               ${DEPLOY_DIR_IMAGE}/${KERNEL_IMAGE_LINK_NAME}.bin
    [ -e       ${DEPLOY_DIR_IMAGE}/${KERNEL_IMAGETYPE}-${KERNEL_IMAGE_NAME}.bin ] && \
        ln -vf ${DEPLOY_DIR_IMAGE}/${KERNEL_IMAGETYPE}-${KERNEL_IMAGE_NAME}.bin \
               ${DEPLOY_DIR_IMAGE}/${KERNEL_IMAGETYPE}
    [ -e       ${DEPLOY_DIR_IMAGE}/modules-${MODULE_TARBALL_NAME}.tgz ] && \
        ln -vf ${DEPLOY_DIR_IMAGE}/modules-${MODULE_TARBALL_NAME}.tgz \
               ${DEPLOY_DIR_IMAGE}/modules-${MODULE_TARBALL_LINK_NAME}.tgz
}
