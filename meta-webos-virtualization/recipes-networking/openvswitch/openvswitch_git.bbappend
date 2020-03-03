# Copyright (c) 2020 LG Electronics, Inc.

EXTENDPRAUTO_append = "webosvirt1"

# ERROR: openvswitch-2.10.0+5563e309b80bbea9bff538e71ecfd7e5e538bab9-r0 do_package_qa: QA Issue: openvswitch rdepends on bash, but it isnt a build dependency, missing bash in DEPENDS or PACKAGECONFIG? [build-deps]
VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS_${PN}_append_class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS_${PN}_remove_class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"
