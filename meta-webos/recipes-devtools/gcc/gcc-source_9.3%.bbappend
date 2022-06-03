# Copyright (c) 2022 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos2"

FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

SRC_URI:append = " \
    file://0001-arm-Clear-canary-value-after-stack_protect_test-PR96.patch \
    file://0002-testsuite-Fix-gcc.target-arm-stack-protector-1.c-for.patch \
    file://0003-arm-Fix-mpure-code-support-mslow-flash-data-for-armv.patch \
    file://0004-Backport-of-the-patch-for-PR-target-91816.patch \
    file://0005-arm-Fix-up-neon_vector_mem_operand-PR97528.patch \
    file://0006-PR47785-Add-support-for-handling-Xassembler-Wa-optio.patch \
    file://0007-arm-xnnpack-neondot-build-error-fix.patch \
"

