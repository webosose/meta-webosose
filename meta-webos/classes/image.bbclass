# Copyright (c) 2017-2025 LG Electronics, Inc.
#
# Intercept the upstream image.bbclass so that when there's
# a webos_deploy_fixup task.
#

require ${COREBASE}/meta/classes/image.bbclass

inherit webos_deploy

do_build[depends] += "virtual/kernel:do_webos_deploy_fixup"
# needed to provide target of bzImage symlink for build_boot_dd function
do_bootdirectdisk[depends] += "virtual/kernel:do_webos_deploy_fixup"
# similarly for WIC, which also uses bzImage
do_image_wic[depends] += "virtual/kernel:do_webos_deploy_fixup"
# for ostree
do_image_ostree[depends] += "virtual/kernel:do_webos_deploy_fixup"

do_webos_deploy_fixup:prepend() {
    [ -e       ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}${IMAGE_NAME_SUFFIX}.ext3 ] && \
        ln -vf ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}${IMAGE_NAME_SUFFIX}.ext3 \
               ${DEPLOY_DIR_IMAGE}/${IMAGE_LINK_NAME}.ext3

    [ -e       ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}${IMAGE_NAME_SUFFIX}.ext4 ] && \
        ln -vf ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}${IMAGE_NAME_SUFFIX}.ext4 \
               ${DEPLOY_DIR_IMAGE}/${IMAGE_LINK_NAME}.ext4

    [ -e       ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}${IMAGE_NAME_SUFFIX}.manifest ] && \
        ln -vf ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}${IMAGE_NAME_SUFFIX}.manifest \
               ${DEPLOY_DIR_IMAGE}/${IMAGE_LINK_NAME}.manifest

    [ -e       ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}${IMAGE_NAME_SUFFIX}.tar.gz ] && \
        ln -vf ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}${IMAGE_NAME_SUFFIX}.tar.gz \
               ${DEPLOY_DIR_IMAGE}/${IMAGE_LINK_NAME}.tar.gz

    [ -e       ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}${IMAGE_NAME_SUFFIX}.tar.bz2 ] && \
        ln -vf ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}${IMAGE_NAME_SUFFIX}.tar.bz2 \
               ${DEPLOY_DIR_IMAGE}/${IMAGE_LINK_NAME}.tar.bz2

    [ -e       ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}${IMAGE_NAME_SUFFIX}.cpio.gz ] && \
        ln -vf ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}${IMAGE_NAME_SUFFIX}.cpio.gz \
               ${DEPLOY_DIR_IMAGE}/${IMAGE_LINK_NAME}.cpio.gz

    [ -e       ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}-dbg${IMAGE_NAME_SUFFIX}.tar.gz ] && \
        ln -vf ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}-dbg${IMAGE_NAME_SUFFIX}.tar.gz \
               ${DEPLOY_DIR_IMAGE}/${IMAGE_LINK_NAME}-dbg.tar.gz

    [ -e       ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.vmdk ] && \
        ln -vf ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.vmdk \
               ${DEPLOY_DIR_IMAGE}/${IMAGE_LINK_NAME}.vmdk

    [ -e       ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.vfat ] && \
        ln -vf ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.vfat \
               ${DEPLOY_DIR_IMAGE}/${IMAGE_LINK_NAME}.vfat

    [ -e       ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.hdddirect ] && \
        ln -vf ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.hdddirect \
               ${DEPLOY_DIR_IMAGE}/${IMAGE_LINK_NAME}.hdddirect

    [ -e       ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.qemuboot.conf ] && \
        ln -vf ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.qemuboot.conf \
               ${DEPLOY_DIR_IMAGE}/${IMAGE_LINK_NAME}.qemuboot.conf

    [ -e       ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}${IMAGE_NAME_SUFFIX}.rpi-sdimg ] && \
        ln -vf ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}${IMAGE_NAME_SUFFIX}.rpi-sdimg \
               ${DEPLOY_DIR_IMAGE}/${IMAGE_LINK_NAME}.rpi-sdimg

    [ -e       ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}${IMAGE_NAME_SUFFIX}.ostree ] && \
        ln -vf ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}${IMAGE_NAME_SUFFIX}.ostree \
               ${DEPLOY_DIR_IMAGE}/${IMAGE_LINK_NAME}.ostree

    [ -e       ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}${IMAGE_NAME_SUFFIX}.ostree.tar.bz2 ] && \
        ln -vf ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}${IMAGE_NAME_SUFFIX}.ostree.tar.bz2 \
               ${DEPLOY_DIR_IMAGE}/${IMAGE_LINK_NAME}.ostree.tar.bz2

    [ -e       ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}${IMAGE_NAME_SUFFIX}.ota-ext4 ] && \
        ln -vf ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}${IMAGE_NAME_SUFFIX}.ota-ext4 \
               ${DEPLOY_DIR_IMAGE}/${IMAGE_LINK_NAME}.ota-ext4

    [ -e       ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}${IMAGE_NAME_SUFFIX}.wic ] && \
        ln -vf ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}${IMAGE_NAME_SUFFIX}.wic \
               ${DEPLOY_DIR_IMAGE}/${IMAGE_LINK_NAME}.wic

    [ -e       ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}${IMAGE_NAME_SUFFIX}.wic.bmap ] && \
        ln -vf ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}${IMAGE_NAME_SUFFIX}.wic.bmap \
               ${DEPLOY_DIR_IMAGE}/${IMAGE_LINK_NAME}.wic.bmap

    [ -e       ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}${IMAGE_NAME_SUFFIX}.wic.bz2 ] && \
        ln -vf ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}${IMAGE_NAME_SUFFIX}.wic.bz2 \
               ${DEPLOY_DIR_IMAGE}/${IMAGE_LINK_NAME}.wic.bz2

    [ -e       ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}${IMAGE_NAME_SUFFIX}.wic.vmdk ] && \
        ln -vf ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}${IMAGE_NAME_SUFFIX}.wic.vmdk \
               ${DEPLOY_DIR_IMAGE}/${IMAGE_LINK_NAME}.wic.vmdk

    [ -e       ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}${IMAGE_NAME_SUFFIX}.wic.vmdk.gz ] && \
        ln -vf ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}${IMAGE_NAME_SUFFIX}.wic.vmdk.gz \
               ${DEPLOY_DIR_IMAGE}/${IMAGE_LINK_NAME}.wic.vmdk.gz

    [ -e       ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.testdata.json ] && \
        ln -vf ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.testdata.json \
               ${DEPLOY_DIR_IMAGE}/${IMAGE_LINK_NAME}.testdata.json
}

# Don't append to IMAGE_LINK_NAME because in webOS we construct IMAGE_LINK_NAME from IMAGE_NAME
# so the -dbg suffix was applied twice, e.g. core-image-minimal-*-dbg-master-svl-666-dbg.tar.gz
#    d.appendVar('IMAGE_LINK_NAME', '-dbg')
def setup_debugfs_variables(d):
    d.appendVar('IMAGE_ROOTFS', '-dbg')
    image_name = d.getVar('IMAGE_NAME')
    webos_image_name_suffix = d.getVar('IMAGE_VERSION_SUFFIX')
    d.setVar('IMAGE_LINK_NAME', image_name+webos_image_name_suffix+'-dbg')
    d.appendVar('IMAGE_NAME','-dbg')
    d.setVar('IMAGE_BUILDING_DEBUGFS', 'true')
    debugfs_image_fstypes = d.getVar('IMAGE_FSTYPES_DEBUGFS')
    if debugfs_image_fstypes:
        d.setVar('IMAGE_FSTYPES', debugfs_image_fstypes)
