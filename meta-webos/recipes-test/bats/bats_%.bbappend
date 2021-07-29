# Copyright (c) 2019-2020 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos1"

VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS:bats:append:class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS:bats:remove:class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"
