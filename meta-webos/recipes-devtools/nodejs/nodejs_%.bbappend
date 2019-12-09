# Copyright (c) 2019 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos3"

VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS_${PN}-npm_append_class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS_${PN}-npm_remove_class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

SRC_URI_append = "\
    file://0001-Patch-on-Flush-function-for-CPU-ARM-added.patch \
    file://0002-implement-function-chroot-in-process-module.patch \
    file://0003-Adds-noexec-option-for-fork-and-spawn-APIs-of-nodejs.patch \
    file://0004-Fixes-process-pid-getter-to-always-get-actual-pid.patch \
    file://0005-Flush-processor-instruction-cash.patch \
    file://0006-Implement-initial-test-suit-for-forked-node.js.patch \
    file://0007-Optimize-V8-s-JSON-parser.patch \
    file://0008-Optimize-V8-s-JSON-parser-second-part.patch \
    file://0009-Provide-option-compile-js-to-node-for-.js-compilatio.patch \
    file://0010-Add-instanceof-check-for-all-Timeout-methods.patch \
    file://0012-Prevent-creating-usr-etc-in-native-sysroot.patch \
    file://0013-Use-human-readable-process-name-for-chrome-inspector.patch \
"
