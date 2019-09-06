# Copyright (c) 2016-2019 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos3"

EXTRA_OECONF += "--without-abi-compliance-checker --without-api-sanity-checker"

VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS_${PN}_append_class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS_${PN}_remove_class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"
