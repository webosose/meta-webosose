# Copyright (c) 2022 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos2"

FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

SRC_URI:append = " \
    file://0001-arm-Remove-use-of-opts_set-in-arm_configure_build_ta.patch \
    file://0002-arm-ensure-the-arch_name-is-always-set-for-the-build.patch \
    file://0003-arm-Don-t-reconfigure-globals-in-arm_configure_build.patch \
    file://0004-arm-reorder-assembler-architecture-directives-PR1017.patch \
"
