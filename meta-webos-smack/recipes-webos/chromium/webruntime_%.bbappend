# Copyright (c) 2020-2023 LG Electronics, Inc.

EXTENDPRAUTO:append = "${@bb.utils.contains('DISTRO_FEATURES', 'smack', 'smack2', '', d)}"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append = "\
    ${@bb.utils.contains('DISTRO_FEATURES', 'smack', 'file://0001-run_app_shell-add-SMACK-labeling-based-on-AppID_79.patch', '', d)} \
"
