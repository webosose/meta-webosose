# Copyright (c) 2019-2023 LG Electronics, Inc.

EXTENDPRAUTO:append = "${@bb.utils.contains('DISTRO_FEATURES', 'smack', 'smack3', '', d)}"

FILESEXTRAPATHS:prepend := "${THISDIR}/linux:"

SRC_URI += "\
    ${@bb.utils.contains('DISTRO_FEATURES', 'smack', 'file://smack.cfg', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'smack-bringup', 'file://smack-bringup.cfg', '', d)} \
"

WEBOS_KERNEL_EXTRACONFIGS += "\
    ${@bb.utils.contains('DISTRO_FEATURES', 'smack', 'smack.cfg', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'smack-bringup', 'smack-bringup.cfg', '', d)} \
"
