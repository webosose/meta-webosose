# Copyright (c) 2019 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos1"

VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS_${PN}-npm_append_class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS_${PN}-npm_remove = "bash"
