# Copyright (c) 2017 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos1"

VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS_${BPN}_append_class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS_${BPN}_remove = "bash"
