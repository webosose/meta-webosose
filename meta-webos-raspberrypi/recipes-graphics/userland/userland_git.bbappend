# Copyright (c) 2017-2020 LG Electronics, Inc.

EXTENDPRAUTO:append:rpi = "webosrpi4"

VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS:${PN}:append:class-target:rpi = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS:${PN}:remove:class-target:rpi = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"
