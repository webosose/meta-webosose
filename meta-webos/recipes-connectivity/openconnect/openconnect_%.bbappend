# Copyright (c) 2019-2025 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos1"

# http://caprica.lgsvl.com:8080/Errors/Details/1447236
VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS:${PN}:append:class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS:${PN}:remove:class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"
