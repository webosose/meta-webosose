# Copyright (c) 2022 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos2"

FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

SRC_URI:append = " \
    file://0001-arm-Don-t-generate-invalid-LDRD-insns.patch \
    file://0002-arm-Clear-canary-value-after-stack_protect_test-PR96.patch \
    file://0003-testsuite-Fix-gcc.target-arm-stack-protector-1.c-for.patch \
    file://0004-arm-Fix-mpure-code-support-mslow-flash-data-for-armv.patch \
    file://0005-arm-Add-support-for-Neoverse-V1-CPU.patch \
    file://0006-arm-Add-support-for-Neoverse-N2-CPU.patch \
    file://0007-arm-Add-missing-part-number-for-Neoverse-V1.patch \
    file://0008-Backport-of-the-patch-for-PR-target-91816.patch \
    file://0009-arm-Fix-up-neon_vector_mem_operand-PR97528.patch \
    file://0010-PR47785-Add-support-for-handling-Xassembler-Wa-optio.patch \
    file://0011-arm-Remove-use-of-opts_set-in-arm_configure_build_ta.patch \
    file://0012-arm-Fix-an-incorrect-warning-when-mcpu-cortex-a55-is.patch \
    file://0013-arm-ensure-the-arch_name-is-always-set-for-the-build.patch \
    file://0014-arm-Don-t-reconfigure-globals-in-arm_configure_build.patch \
    file://0015-arm-reorder-assembler-architecture-directives-PR1017.patch \
"
