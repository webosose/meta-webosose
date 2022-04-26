# Copyright (c) 2020-2022 LG Electronics, Inc.

EXTENDPRAUTO:append = "${@bb.utils.contains('DISTRO_FEATURES', 'smack', 'smack1', '', d)}"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI = "${@bb.utils.contains('DISTRO_FEATURES', 'smack', 'file://smack_labels_default', '', d)}"

SMACK_LABELS_FILE = "${@bb.utils.contains('DISTRO_FEATURES', 'smack', '${WORKDIR}/smack_labels_default', '', d)}"

inherit webos_smack_labeling

do_rootfs[prefuncs] += "${@bb.utils.contains('DISTRO_FEATURES', 'smack', 'do_fetch do_unpack', '', d)}"

ROOTFS_POSTPROCESS_COMMAND += "\
    ${@bb.utils.contains('DISTRO_FEATURES', 'smack', ' do_smack_labeling ; ', '', d)} \
"
