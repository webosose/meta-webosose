# Copyright (c) 2020-2023 LG Electronics, Inc.

EXTENDPRAUTO:append = "webosvirt1"

# ERROR: singularity-2.3.1+gitAUTOINC+e214d4ebf0-r0 do_package_qa: QA Issue: singularity rdepends on bash, but it isnt a build dependency, missing bash in DEPENDS or PACKAGECONFIG? [build-deps]
VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS:${PN}:append:class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS:${PN}:remove:class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"
