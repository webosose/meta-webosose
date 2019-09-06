# Copyright (c) 2019 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos1"

# http://caprica.lgsvl.com:8080/Errors/Details/1447233
VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS_${PN}-leftovers_append_class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS_${PN}-leftovers_remove_class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"
