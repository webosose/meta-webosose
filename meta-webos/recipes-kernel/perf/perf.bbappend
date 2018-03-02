# Copyright (c) 2017 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos2"

VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS_${PN}_append_class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS_${PN}_remove = "bash"
RDEPENDS_${PN}-python_append_class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS_${PN}-python_remove = "bash"
RDEPENDS_${PN}-archive_append_class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS_${PN}-archive_remove = "bash"
RDEPENDS_${PN}-perl_append_class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS_${PN}-perl_remove = "bash"

