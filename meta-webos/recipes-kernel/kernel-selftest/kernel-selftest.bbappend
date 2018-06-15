# Copyright (c) 2018 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos1"

VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS_${PN}_append_class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS_${PN}_remove = "bash"

# cannot use bpf for kernels older than 4.10
PACKAGECONFIG_remove = "bpf"
