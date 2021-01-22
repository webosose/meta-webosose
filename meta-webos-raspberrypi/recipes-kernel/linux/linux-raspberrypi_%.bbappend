# Copyright (c) 2017-2021 LG Electronics, Inc.

EXTENDPRAUTO_append = "webosrpi26"

CMDLINE_append = " rw cgroup_memory=1 cgroup_enable=memory"
CMDLINE_remove_sota = "root=/dev/mmcblk0p2 rootfstype=ext4 rootwait rw"

SHRT_VER = "${@oe.utils.trim_version('${PV}', 2)}"
FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}-${SHRT_VER}:${THISDIR}/${BPN}:"

SRC_URI += "\
    file://ostree.cfg \
    ${@bb.utils.contains('DISTRO_FEATURES', 'virtualization', 'file://docker.cfg', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'virtualization', 'file://ebtables.cfg', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'virtualization', 'file://lxc.cfg', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'virtualization', 'file://vswitch.cfg', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'virtualization', 'file://xt-checksum.cfg', '', d)} \
    file://bridge.cfg \
    file://gps.cfg \
"

KERNEL_MODULE_AUTOLOAD_append = " \
    media \
    snd-usb-audio \
    uinput \
    uvcvideo \
    videodev \
    ${@bb.utils.contains('DISTRO_FEATURES', 'virtualization', 'nf_conntrack_ipv6', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'virtualization', 'openvswitch', '', d)} \
    bridge \
"

do_deploy_append() {
    # Remove kernel image link in meta-webos/classes/kernel.bbclass
    # However the image link is required in raspberrypi
    ln -sf ${type}-${KERNEL_IMAGE_NAME}.bin ${DEPLOYDIR}/${type}-${KERNEL_IMAGE_LINK_NAME}.bin
}
