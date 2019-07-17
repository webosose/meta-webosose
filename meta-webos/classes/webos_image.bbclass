# Copyright (c) 2012-2019 LG Electronics, Inc.

# IMAGE_FEATURES controls the contents of webOS OSE images
#
# By default we install packagegroup-core-boot and packagegroup-webos-extended packages
# this gives full working webOS OSE image.
#
# Available IMAGE_FEATURES:
#
# - webos-devel         - Additional packages useful for during development
# - webos-extended      - webOS components
# - webos-test          - Additional packages for running unit and integration tests
#
# and IMAGE_FEATURES from core-image

FEATURE_PACKAGES_webos-extended = "packagegroup-webos-extended"
FEATURE_PACKAGES_webos-devel = "packagegroup-webos-devel"
FEATURE_PACKAGES_webos-test = "packagegroup-webos-test"

WEBOS_IMAGE_DEFAULT_SSH_IMAGE_FEATURE = "ssh-server-dropbear"
WEBOS_IMAGE_DEFAULT_FEATURES = "package-management"

WEBOS_IMAGE_DEFAULT_FEATURES_append = "${@ ' ${WEBOS_IMAGE_DEFAULT_SSH_IMAGE_FEATURE}' if d.getVar('WEBOS_DISTRO_PRERELEASE',True) != '' else ''}"
WEBOS_IMAGE_DEFAULT_FEATURES_append_emulator = " ${WEBOS_IMAGE_DEFAULT_SSH_IMAGE_FEATURE}"

IMAGE_FEATURES[validitems] += "webos-production-image"
WEBOS_IMAGE_DEFAULT_FEATURES_append = "${@ ' webos-production-image' if d.getVar('WEBOS_DISTRO_PRERELEASE',True) == '' else ''}"

WEBOS_IMAGE_BASE_INSTALL = '\
    packagegroup-core-boot \
    packagegroup-webos-extended \
    \
    ${WEBOS_IMAGE_EXTRA_INSTALL} \
    '

WEBOS_IMAGE_EXTRA_INSTALL ?= ""

IMAGE_INSTALL ?= "${WEBOS_IMAGE_BASE_INSTALL}"

# Add ${webos_sysconfdir}/build/image-name during image construction that contains the image name
ROOTFS_POSTPROCESS_COMMAND += "rootfs_set_image_name ; clean_python_installation ; verify_acg ; "

# Can be used to echo image name to ${webos_sysconfdir}/build/image-name
rootfs_set_image_name () {
    mkdir -p ${IMAGE_ROOTFS}${webos_sysconfdir}/build
    echo ${IMAGE_BASENAME} > ${IMAGE_ROOTFS}${webos_sysconfdir}/build/image-name
}

# cleanup python installation
clean_python_installation () {
    for p in `find ${IMAGE_ROOTFS}${libdir} -name "*pyo" `
    do
        rm -f $p
    done
}

# run LS2 ACG verification code
python verify_acg () {
    def herror(c, m):
        package_qa_handle_error(c, m, d)

    import verify_ls2_acg
    verify_ls2_acg.handle_error = herror
    if not verify_ls2_acg.Verify("${IMAGE_ROOTFS}"):
        bb.note("LS2 hub config not found, ACG verification skipped")
}

# A hook function to support read-only-rootfs IMAGE_FEATURES
webos_read_only_rootfs_hook () {
    set -x
    # Tweak the mount option and fs_passno for rootfs in fstab
    sed -i -e '/^[#[:space:]]*\/dev\/root/{s/rw/ro/;s/\([[:space:]]*[[:digit:]]\)\([[:space:]]*\)[[:digit:]]$/\1\20/}' ${IMAGE_ROOTFS}/etc/fstab

    # Change the value of ROOTFS_READ_ONLY in /etc/default/rcS to yes
    if [ -e ${IMAGE_ROOTFS}/etc/default/rcS ]; then
         sed -i 's/\(^[ \t]*ROOTFS_READ_ONLY=\)no/\1=yes/' ${IMAGE_ROOTFS}/etc/default/rcS
         if ! grep "^[ \t]*ROOTFS_READ_ONLY=yes[ \t]*$" ${IMAGE_ROOTFS}/etc/default/rcS ; then
             bbfatal "Failed to change ROOTFS_READ_ONLY settings in ${IMAGE_ROOTFS}/etc/default/rcS"
         fi
    fi
}
ROOTFS_POSTPROCESS_COMMAND += '${@bb.utils.contains("IMAGE_FEATURES", "read-only-rootfs", "webos_read_only_rootfs_hook ; ", "", d)}'

inherit core-image
inherit webos_machine_impl_dep
inherit webos_filesystem_paths
inherit webos_prerelease_dep
do_rootfs[depends] += "libpbnjson-native:do_populate_sysroot"

WKS_FILE = "webos-qemux86-directdisk.wks"

# Build only wic.vmdk for qemux86*, otherwise wic.vmdk might conflict with tar.gz and cause errors like:
# | tar: ./usr/lib/perl/5.24.1/unicore/lib/Bc/EN.pl: file changed as we read it
IMAGE_FSTYPES_qemux86 = "wic.vmdk"
IMAGE_FSTYPES_qemux86-64 = "wic.vmdk"

# A workaround for controlling '/media' policy of meta-updater
IMAGE_CMD_ostree_prepend_sota() {
    mv ${IMAGE_ROOTFS}/media ${IMAGE_ROOTFS}/.media || true
}
IMAGE_CMD_ostree_append_sota() {
    mv ${IMAGE_ROOTFS}/.media ${IMAGE_ROOTFS}/media
    mkdir media
}
IMAGE_CMD_ota_append_sota() {
    # Copy /var, /media that was ignored by ostree
    cp -a ${IMAGE_ROOTFS}/var/luna ${OTA_SYSROOT}/ostree/deploy/${OSTREE_OSNAME}/var || true
    cp -a ${IMAGE_ROOTFS}/var/systemd ${OTA_SYSROOT}/ostree/deploy/${OSTREE_OSNAME}/var || true
    cp -a ${IMAGE_ROOTFS}/media ${OTA_SYSROOT} || true

    # make ostree hotfix mode as default (set /usr to R/W mode)
    echo "unlocked=hotfix" >> ${OTA_SYSROOT}/ostree/deploy/${OSTREE_OSNAME}/deploy/${ostree_target_hash}.0.origin
    mkdir -v -m 0755 -p ${OTA_SYSROOT}/ostree/deploy/${OSTREE_OSNAME}/deploy/${ostree_target_hash}.0/.usr-ovl-upper
    mkdir -v -m 0755 -p ${OTA_SYSROOT}/ostree/deploy/${OSTREE_OSNAME}/deploy/${ostree_target_hash}.0/.usr-ovl-work
}
