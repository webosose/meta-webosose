# Copyright (c) 2017 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos2"

VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS_${PN}_append_class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS_${PN}_remove = "bash"
RDEPENDS_${PN}-client_append_class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS_${PN}-client_remove = "bash"
