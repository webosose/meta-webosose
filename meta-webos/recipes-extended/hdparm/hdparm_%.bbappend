# Copyright (c) 2017 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos2"

VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS_wiper_append_class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS_wiper_remove = "bash"

VIRTUAL-RUNTIME_stat ?= "stat"
RDEPENDS_wiper_append_class-target = " ${VIRTUAL-RUNTIME_stat}"
RDEPENDS_wiper_remove = "stat"
