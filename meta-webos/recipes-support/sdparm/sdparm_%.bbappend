# Copyright (c) 2017-2019 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos2"

VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS_${PN}-scripts_append_class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS_${PN}-scripts_remove = "bash"
