# Copyright (c) 2016-2022 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos4"

VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS:${PN}-build:append:class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS:${PN}-build:remove:class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"
