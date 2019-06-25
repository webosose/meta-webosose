# Copyright (c) 2017-2019 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos3"

VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS_${PN}-ctdb-tests_append_class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS_${PN}-ctdb-tests_remove = "bash"
