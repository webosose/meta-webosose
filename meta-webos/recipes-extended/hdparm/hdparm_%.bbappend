# Copyright (c) 2017-2022 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos2"

VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS:wiper:append:class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS:wiper:remove:class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"

VIRTUAL-RUNTIME_stat ?= "stat"
RDEPENDS:wiper:append:class-target = " ${VIRTUAL-RUNTIME_stat}"
RDEPENDS:wiper:remove:class-target = "stat"
