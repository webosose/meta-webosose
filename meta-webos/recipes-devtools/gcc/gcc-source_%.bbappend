# Copyright (c) 2022 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos2"

FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

SRC_URI:append = " \
    file://0001-arm-Clear-canary-value-after-stack_protect_test-PR96.patch \
    file://0002-testsuite-Fix-gcc.target-arm-stack-protector-1.c-for.patch \
    file://0003-arm-Fix-mpure-code-support-mslow-flash-data-for-armv.patch \
    file://0004-arm-Add-support-for-Neoverse-V1-CPU.patch \
    file://0005-arm-Add-support-for-Neoverse-N2-CPU.patch \
    file://0006-arm-Add-missing-part-number-for-Neoverse-V1.patch \
    file://0007-arm-Fix-up-neon_vector_mem_operand-PR97528.patch \
    file://0008-arm-Remove-use-of-opts_set-in-arm_configure_build_ta.patch \
    file://0009-arm-ensure-the-arch_name-is-always-set-for-the-build.patch \
    file://0010-arm-Don-t-reconfigure-globals-in-arm_configure_build.patch \
    file://0011-arm-Add-nomve-and-nomve.fp-options-to-mcpu-cortex-m5.patch \
    file://0012-arm-reorder-assembler-architecture-directives-PR1017.patch \
"
