# Copyright (c) 2018 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos1"

VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS_mtd-utils-tests_append_class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS_mtd-utils-tests_remove = "bash"
