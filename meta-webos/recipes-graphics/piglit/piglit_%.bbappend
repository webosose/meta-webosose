# Copyright (c) 2019 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos1"

# http://caprica.lgsvl.com:8080/Errors/Details/1447235
VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS_${PN}_append_class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS_${PN}_remove_class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"
