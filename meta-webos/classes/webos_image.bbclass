# Copyright (c) 2012-2023 LG Electronics, Inc.

# IMAGE_FEATURES controls the contents of webOS OSE images
#
# Available IMAGE_FEATURES:
#
# webos-systemapps:         webOS system app packages
# webos-testapps:           webOS test app packages
# webos-extended:           webOS extended packages
#
# webos-devel:              Additional packages useful for during development
# webos-test:               Additional packages for running unit and integration tests
#
# webos-extract-ls2-api:    Add task to extract luna-service2 api list
# webos-production-image:   Specific features to productize
#
# and IMAGE_FEATURES from core-image
IMAGE_FEATURES[validitems] = "webos-systemapps webos-testapps webos-extended webos-devel webos-test webos-extract-ls2-api webos-production-image"

inherit webos_image_${WEBOS_TARGET_DISTRO_VARIANT}

FEATURE_PACKAGES_webos-systemapps = "packagegroup-webos-systemapps"
FEATURE_PACKAGES_webos-testapps = "packagegroup-webos-testapps"
FEATURE_PACKAGES_webos-extended = "packagegroup-webos-extended"
FEATURE_PACKAGES_webos-devel = "packagegroup-webos-devel"
FEATURE_PACKAGES_webos-test = "packagegroup-webos-test"

WEBOS_IMAGE_DEFAULT_SSH_IMAGE_FEATURE = "ssh-server-dropbear"
WEBOS_IMAGE_DEFAULT_FEATURES = "package-management"

WEBOS_IMAGE_DEFAULT_FEATURES:append = "${@ ' ${WEBOS_IMAGE_DEFAULT_SSH_IMAGE_FEATURE}' if d.getVar('WEBOS_DISTRO_PRERELEASE') != '' else ''}"
WEBOS_IMAGE_DEFAULT_FEATURES:append:emulator = " ${WEBOS_IMAGE_DEFAULT_SSH_IMAGE_FEATURE}"

WEBOS_IMAGE_DEFAULT_FEATURES:append = "${@ ' webos-production-image' if d.getVar('WEBOS_DISTRO_PRERELEASE') == '' else ''}"

WEBOS_IMAGE_BASE_INSTALL = " \
    packagegroup-core-boot \
    ${WEBOS_IMAGE_EXTRA_INSTALL} \
"

WEBOS_IMAGE_EXTRA_INSTALL = " \
    ${@bb.utils.contains('DISTRO_FEATURES', 'webos-aiframework', 'packagegroup-webos-ml', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'webos-audio', 'packagegroup-webos-audio', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'webos-bluetooth', 'packagegroup-webos-bluetooth', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'webos-camera', 'packagegroup-webos-camera', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'webos-connectivity', 'packagegroup-webos-connectivity', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'webos-diagnostics', 'packagegroup-webos-diagnostics', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'webos-graphics', 'packagegroup-webos-graphics', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'webos-i18n', 'packagegroup-webos-i18n', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'webos-media', 'packagegroup-webos-media', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'webos-media-drm', 'packagegroup-webos-media-drm', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'webos-screencast', 'packagegroup-webos-screencast', '', d)} \
"

IMAGE_INSTALL ?= "${WEBOS_IMAGE_BASE_INSTALL}"

# webOS supports the generation of oss package information file.
# $ bitbake -c write_oss_pkg_info <image>
IMAGE_CLASSES += "webos_oss_pkg_info pkg_dependency webos_ls2_api_info ${@bb.utils.contains('IMAGE_FEATURES', 'webos-extract-ls2-api', 'webos_ls2_api_list', '', d)}"

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
        oe.qa.handle_error(c, m, d)

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

# Build only wic.vmdk for qemux86*, otherwise wic.vmdk might conflict with tar.gz and cause errors like:
# | tar: ./usr/lib/perl/5.24.1/unicore/lib/Bc/EN.pl: file changed as we read it
IMAGE_FSTYPES:qemux86 = "wic.vmdk"
IMAGE_FSTYPES:qemux86-64 = "wic.vmdk"

# A workaround for controlling '/media' and '/opt' policy of meta-updater
IMAGE_CMD:ostree:prepend:sota() {
    echo "Moving ${IMAGE_ROOTFS} /media and /opt to /.media and /.opt to prevent IMAGE_CMD:ostree replacing them with symlinks to /var/rootdirs/"
    mv -v ${IMAGE_ROOTFS}/media ${IMAGE_ROOTFS}/.media || true
    mv -v ${IMAGE_ROOTFS}/opt ${IMAGE_ROOTFS}/.opt || true
}
IMAGE_CMD:ostree:append:sota() {
    echo "Moving ${IMAGE_ROOTFS} /.media and /.opt back to /media and /opt"
    mv -v ${IMAGE_ROOTFS}/.media ${IMAGE_ROOTFS}/media
    mv -v ${IMAGE_ROOTFS}/.opt ${IMAGE_ROOTFS}/opt

    # Remove the symlinks to var/rootdirs/media, var/rootdirs/opt created in IMAGE_CMD:ostree and replace them with backup versions of the real directories saved above in IMAGE_CMD:ostree
    rm -fv ${OSTREE_ROOTFS}/media ${OSTREE_ROOTFS}/opt
    cp -av ${IMAGE_ROOTFS}/media ${OSTREE_ROOTFS} || true
    cp -av ${IMAGE_ROOTFS}/opt ${OSTREE_ROOTFS} || true
}
IMAGE_CMD:ota:append:sota() {
    # Copy /var that was ignored by ostree
    cp -av ${IMAGE_ROOTFS}/var/luna ${OTA_SYSROOT}/ostree/deploy/${OSTREE_OSNAME}/var || true
    cp -av ${IMAGE_ROOTFS}/var/systemd ${OTA_SYSROOT}/ostree/deploy/${OSTREE_OSNAME}/var || true
    cp -av ${IMAGE_ROOTFS}/var/PMS ${OTA_SYSROOT}/ostree/deploy/${OSTREE_OSNAME}/var || true
    # Copy /media /opt that were ignored by ostree
    cp -av ${IMAGE_ROOTFS}/media ${OTA_SYSROOT} || true
    cp -av ${IMAGE_ROOTFS}/opt ${OTA_SYSROOT} || true

    # make ostree hotfix mode as default (set /usr to R/W mode)
    echo "unlocked=hotfix" >> ${OTA_SYSROOT}/ostree/deploy/${OSTREE_OSNAME}/deploy/${ostree_target_hash}.0.origin
    mkdir -v -m 0755 -p ${OTA_SYSROOT}/ostree/deploy/${OSTREE_OSNAME}/deploy/${ostree_target_hash}.0/.usr-ovl-upper
    mkdir -v -m 0755 -p ${OTA_SYSROOT}/ostree/deploy/${OSTREE_OSNAME}/deploy/${ostree_target_hash}.0/.usr-ovl-work
}
