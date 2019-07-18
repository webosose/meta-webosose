# Copyright (c) 2019 LG Electronics, Inc.

EXTENDPRAUTO_append = "${@bb.utils.contains('DISTRO_FEATURES', 'smack', 'smack1', '', d)}"

FILESEXTRAPATHS_prepend := "${THISDIR}/linux:"

SRC_URI += "\
    ${@bb.utils.contains('DISTRO_FEATURES', 'smack', 'file://smack.cfg', '', d)} \
    ${@bb.utils.contains('IMAGE_FEATURES', 'smack-bringup', 'file://smack-bringup.cfg', '', d)} \
"

WEBOS_KERNEL_EXTRACONFIGS += "\
    ${@bb.utils.contains('DISTRO_FEATURES', 'smack', 'smack.cfg', '', d)} \
    ${@bb.utils.contains('IMAGE_FEATURES', 'smack-bringup', 'smack-bringup.cfg', '', d)} \
"

do_configure_prepend_rpi() {
    if ${@bb.utils.contains('DISTRO_FEATURES', 'smack', 'true', 'false', d)}; then
        cat "${WORKDIR}/smack.cfg" >> "${WORKDIR}/defconfig"
    fi
    if ${@bb.utils.contains('IMAGE_FEATURES', 'smack', 'true', 'false', d)}; then
        cat "${WORKDIR}/smack-bringup.cfg" >> "${WORKDIR}/defconfig"
    fi
}
