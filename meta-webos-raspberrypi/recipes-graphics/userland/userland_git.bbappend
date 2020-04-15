# Copyright (c) 2017-2020 LG Electronics, Inc.

EXTENDPRAUTO_append_rpi = "webosrpi4"

VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS_${PN}_append_class-target_rpi = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS_${PN}_remove_class-target_rpi = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"
