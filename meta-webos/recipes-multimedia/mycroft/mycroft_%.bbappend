# Copyright (c) 2021-2025 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos1"

# ERROR: mycroft-19.8.1-r0 do_package_qa: QA Issue: mycroft rdepends on bash, but it isn't a build dependency, missing bash in DEPENDS or PACKAGECONFIG? [build-deps]
VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS:${PN}:append:class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS:${PN}:remove:class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"
