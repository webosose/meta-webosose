# Copyright (c) 2017-2025 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos2"

VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS:${PN}-sensord:append:class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS:${PN}-sensord:remove:class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"
RDEPENDS:${PN}-fancontrol:append:class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS:${PN}-fancontrol:remove:class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"
RDEPENDS:${PN}-pwmconfig:append:class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS:${PN}-pwmconfig:remove:class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"
