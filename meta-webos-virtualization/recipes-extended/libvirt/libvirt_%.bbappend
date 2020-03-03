# Copyright (c) 2020 LG Electronics, Inc.

EXTENDPRAUTO_append = "webosvirt1"

# ERROR: libvirt-4.7.0-r0 do_package_qa: QA Issue: libvirt-ptest rdepends on bash, but it isn't a build dependency, missing bash in DEPENDS or PACKAGECONFIG? [build-deps]
VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS_${PN}-ptest_append_class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS_${PN}-ptest_remove_class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"
