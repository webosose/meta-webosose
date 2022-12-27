# Copyright (c) 2020-2023 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos1"

# ERROR: rt-tests-1_1.9-r0 do_package_qa: QA Issue: rt-tests rdepends on bash, but it isn't a build dependency, missing bash in DEPENDS or PACKAGECONFIG? [build-deps]
VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS:${PN}:append:class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS:${PN}:remove:class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"
