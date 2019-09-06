# Copyright (c) 2012-2019 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos11"
FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

SRC_URI += "file://e2fsck.conf"

do_install_append() {
    install -d ${D}${sysconfdir}
    install -v -m 0644 ${WORKDIR}/e2fsck.conf ${D}${sysconfdir}/
}

VIRTUAL-RUNTIME_bash ?= "bash"
# Don't use ${PN} in this one, because original recipe already uses RDEPENDS_e2fsprogs
# and using ${PN} here would overwrite it:
# 2fsprogs_1.43.bb: Variable key RDEPENDS_${PN} ( ${VIRTUAL-RUNTIME_bash}) replaces original key RDEPENDS_e2fsprogs (e2fsprogs-badblocks).
RDEPENDS_e2fsprogs_append_class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS_${PN}-ptest_append_class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS_${PN}-ptest_remove_class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"
