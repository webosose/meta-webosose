# Copyright (c) 2019 LG Electronics, Inc.

EXTENDPRAUTO:append = "${@bb.utils.contains('DISTRO_FEATURES', 'smack', 'smack1', '', d)}"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += "\
    ${@bb.utils.contains('DISTRO_FEATURES', 'smack', 'file://0001-Add-SMACK-security-labeling.patch', '', d)} \
"
