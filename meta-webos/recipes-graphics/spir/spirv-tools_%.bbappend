# Copyright (c) 2021-2025 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos1"

# ERROR: spirv-tools-2020.6-r0 do_package_qa: QA Issue: spirv-tools-lesspipe rdepends on bash, but it isn't a build dependency, missing bash in DEPENDS or PACKAGECONFIG? [build-deps]
VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS:${PN}-lesspipe:append:class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS:${PN}-lesspipe:remove:class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"
