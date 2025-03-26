# Copyright (c) 2024-2025 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos1"

# http://gecko.lge.com:8000/Errors/Details/784055
VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS:${PN}-scripts:append:class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS:${PN}-scripts:remove:class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"
