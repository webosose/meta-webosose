# Copyright (c) 2017-2018 LG Electronics, Inc.

EXTENDPRAUTO_append = "webosrpi17"

CMDLINE_append = " rw"

SHRT_VER = "${@oe.utils.trim_version('${PV}', 2)}"
FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}-${SHRT_VER}:"

SRC_URI_append = " \
    file://0001-Remove-master-priviliges-for-drm-calls.patch \
    file://0002-Extend-drm-plane-properties.patch \
    file://0003-Fix-redundant-kernel-console-log-spew.patch \
    file://0004-Patch-permissions-issue-with-drm-clients.patch \
    file://0005-vc4-kms-Add-bcm2710-to-compatibility-list.patch \
    file://0006-miniuart-Add-bcm2709-and-bcm2710-to-compatibility.patch \
    file://0007-Fix-kernel-division-by-0-exception.patch \
    file://0008-Fix-scaling-and-fb-association-on-a-plane-asynchrono.patch \
    file://0009-Fix-scaling-not-working-for-specific-display-windows.patch \
    file://0010-Add-API-to-set-only-the-source-rectangle.patch \
    file://0011-Implement-page-flipping-changes-in-kernel.patch \
    file://0012-Set-CPU-governor-to-ondemand.patch \
    file://0013-Fix-kernel-panic-on-10hr-YouTube-playback.patch \
    file://0014-DRM-custom-prop.patch \
    file://0015-DRM-forcing-permissions.patch \
    file://0016-Enable-ZRAM-feature.patch \
"

KERNEL_MODULE_AUTOLOAD_append = " \
    media \
    snd-usb-audio \
    uinput \
    uvcvideo \
    videodev \
"

do_deploy_append() {
    # Remove kernel image link in meta-webos/classes/kernel.bbclass
    # However the image link is required in raspberrypi
    ln -sf ${type}-${KERNEL_IMAGE_BASE_NAME}.bin ${DEPLOYDIR}/${type}-${KERNEL_IMAGE_SYMLINK_NAME}.bin
}
