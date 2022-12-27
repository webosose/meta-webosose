# Copyright (c) 2018-2023 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos1"

VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS:mtd-utils-tests:append:class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS:mtd-utils-tests:remove:class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"
