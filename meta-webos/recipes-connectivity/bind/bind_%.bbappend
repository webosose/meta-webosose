# Copyright (c) 2018-2019 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos1"

VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS_${PN}_append_class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS_${PN}_remove_class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"
RDEPENDS_${PN}-utils_append_class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS_${PN}-utils_remove_class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"
