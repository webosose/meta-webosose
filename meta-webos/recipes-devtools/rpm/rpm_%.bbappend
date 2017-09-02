# Copyright (c) 2016-2017 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos3"

EXTRA_OECONF += "--without-abi-compliance-checker --without-api-sanity-checker"

VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS_${PN}_append_class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS_${PN}_remove = "bash"
