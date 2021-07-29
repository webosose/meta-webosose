# Copyright (c) 2017-2019 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos2"

VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS:${PN}-scripts:append:class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS:${PN}-scripts:remove:class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"
