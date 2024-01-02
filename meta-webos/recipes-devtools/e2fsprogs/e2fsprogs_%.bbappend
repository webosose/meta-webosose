# Copyright (c) 2012-2024 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos11"
FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

SRC_URI += "file://e2fsck.conf"

do_install:append() {
    install -d ${D}${sysconfdir}
    install -v -m 0644 ${WORKDIR}/e2fsck.conf ${D}${sysconfdir}/
}

VIRTUAL-RUNTIME_bash ?= "bash"
# Don't use ${PN} in this one, because original recipe already uses RDEPENDS:e2fsprogs
# and using ${PN} here would overwrite it:
# 2fsprogs_1.43.bb: Variable key RDEPENDS:${PN} ( ${VIRTUAL-RUNTIME_bash}) replaces original key RDEPENDS:e2fsprogs (e2fsprogs-badblocks).
RDEPENDS:e2fsprogs:append:class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS:${PN}-ptest:append:class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS:${PN}-ptest:remove:class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"
RDEPENDS:e2fsprogs-e2scrub:append:class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS:e2fsprogs-e2scrub:remove:class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"
