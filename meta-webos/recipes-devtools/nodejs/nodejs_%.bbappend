# Copyright (c) 2019-2020 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos6"

VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS_${PN}-npm_append_class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS_${PN}-npm_remove_class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

SRC_URI += "file://0013-Use-human-readable-process-name-for-chrome-inspector.patch"
