# Copyright (c) 2017-2019 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos2"

VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS_${PN}_append_class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS_${PN}_remove = "bash"
RDEPENDS_${PN}-raidmon_append_class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS_${PN}-raidmon_remove = "bash"
RDEPENDS_${PN}-diskmon_append_class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS_${PN}-diskmon_remove = "bash"
