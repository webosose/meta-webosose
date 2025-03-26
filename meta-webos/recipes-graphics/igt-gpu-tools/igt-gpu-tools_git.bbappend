# Copyright (c) 2020-2025 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos1"

VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS:${PN}:append:class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS:${PN}:remove:class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"
RDEPENDS:${PN}-tests:append:class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS:${PN}-tests:remove:class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"
