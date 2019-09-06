# Copyright (c) 2017-2019 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos2"

VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS_wiper_append_class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS_wiper_remove_class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"

VIRTUAL-RUNTIME_stat ?= "stat"
RDEPENDS_wiper_append_class-target = " ${VIRTUAL-RUNTIME_stat}"
RDEPENDS_wiper_remove_class-target = "stat"
