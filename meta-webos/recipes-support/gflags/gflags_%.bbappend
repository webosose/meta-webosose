# Copyright (c) 2018-2024 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos1"

VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS:${PN}-bash-completion:append:class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS:${PN}-bash-completion:remove:class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"
