# Copyright (c) 2017-2019 LG Electronics, Inc.

EXTENDPRAUTO_append = "webosrpi20"

CMDLINE_append = " rw"

SHRT_VER = "${@oe.utils.trim_version('${PV}', 2)}"
FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}-${SHRT_VER}:${THISDIR}/${BPN}:"

# security.cfg - enable security features. Should be applied to default configuration.
SRC_URI += "\
    file://security.cfg \
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
    ln -sf ${type}-${KERNEL_IMAGE_NAME}.bin ${DEPLOYDIR}/${type}-${KERNEL_IMAGE_LINK_NAME}.bin
}

do_configure_prepend() {
    cat "${WORKDIR}/security.cfg" >> "${WORKDIR}/defconfig"
}
