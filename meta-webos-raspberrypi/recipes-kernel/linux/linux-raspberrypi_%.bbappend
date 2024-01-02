# Copyright (c) 2017-2024 LG Electronics, Inc.

EXTENDPRAUTO:append = "webosrpi34"

CMDLINE:append = " rw cgroup_memory=1 cgroup_enable=memory swapaccount=1"

SHRT_VER = "${@oe.utils.trim_version('${PV}', 2)}"
FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}-${SHRT_VER}:${THISDIR}/${BPN}:"

SRC_URI += "\
    file://0001-bcm2835-v4l2-codec-fix-vchiq-mmal-renable.patch \
    file://0001-kernel-seed-voicecard.patch \
    file://0002-seed-voicecard-Update-from-HinTak-to-support-v6.1-ke.patch;minver=6.1.0 \
    ${@bb.utils.contains('DISTRO_FEATURES', 'virtualization', 'file://docker.cfg', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'virtualization', 'file://ebtables.cfg', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'virtualization', 'file://lxc.cfg', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'virtualization', 'file://vswitch.cfg', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'virtualization', 'file://xt-checksum.cfg', '', d)} \
    file://bridge.cfg \
    file://gps.cfg \
    file://usb-serial-ftdi-sio.cfg \
    file://oomd.cfg \
    file://ebpf.cfg \
    file://governor.cfg \
    file://ntfs.cfg \
    file://zram.cfg \
    file://security.cfg \
"

KERNEL_MODULE_AUTOLOAD:append = " \
    i2c-dev \
    spidev \
    spi_bcm2835 \
    media \
    snd-usb-audio \
    uinput \
    uvcvideo \
    videodev \
    ${@bb.utils.contains('DISTRO_FEATURES', 'virtualization', 'nf_conntrack_ipv6', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'virtualization', 'openvswitch', '', d)} \
    bridge \
    snd-soc-ac108 \
    snd-soc-seeed-voicecard \
    snd-soc-bcm2835-i2s \
"

do_deploy:append() {
    # Remove kernel image link in meta-webos/classes/kernel.bbclass
    # However the image link is required in raspberrypi
    ln -sf ${type}-${KERNEL_IMAGE_NAME}.bin ${DEPLOYDIR}/${type}-${KERNEL_IMAGE_LINK_NAME}.bin
}

export CCACHE_MAXSIZE = "1G"
