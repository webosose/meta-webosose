# Copyright (c) 2018 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos1"

VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS_${PN}-bash-completion_append_class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS_${PN}-bash-completion_remove = "bash"
