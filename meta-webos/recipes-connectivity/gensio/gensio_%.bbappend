# Copyright (c) 2020 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos1"

# ERROR: gensio-1.5.3+gitAUTOINC+95ec1ab31e-r0 do_package_qa: QA Issue: gensio rdepends on bash, but it isn't a build dependency, missing bash in DEPENDS or PACKAGECONFIG? [build-deps]
VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS:${PN}:append:class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS:${PN}:remove:class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"
