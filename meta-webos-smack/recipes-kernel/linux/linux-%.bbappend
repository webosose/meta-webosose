# Copyright (c) 2019-2022 LG Electronics, Inc.

EXTENDPRAUTO:append = "${@bb.utils.contains('DISTRO_FEATURES', 'smack', 'smack2', '', d)}"

FILESEXTRAPATHS:prepend := "${THISDIR}/linux:"

SRC_URI += "\
    ${@bb.utils.contains('DISTRO_FEATURES', 'smack', 'file://smack.cfg', '', d)} \
    ${@bb.utils.contains('IMAGE_FEATURES', 'smack-bringup', 'file://smack-bringup.cfg', '', d)} \
"

WEBOS_KERNEL_EXTRACONFIGS += "\
    ${@bb.utils.contains('DISTRO_FEATURES', 'smack', 'smack.cfg', '', d)} \
    ${@bb.utils.contains('IMAGE_FEATURES', 'smack-bringup', 'smack-bringup.cfg', '', d)} \
"
