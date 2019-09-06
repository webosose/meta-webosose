# Copyright (c) 2017-2019 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos2"

VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS_${PN}-sensord_append_class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS_${PN}-sensord_remove_class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"
RDEPENDS_${PN}-fancontrol_append_class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS_${PN}-fancontrol_remove_class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"
RDEPENDS_${PN}-pwmconfig_append_class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS_${PN}-pwmconfig_remove_class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"
