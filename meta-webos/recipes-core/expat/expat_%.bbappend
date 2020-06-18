# Copyright (c) 2020-2022 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos1"

VIRTUAL-RUNTIME_bash ?= "bash"
# ERROR: expat-2.2.9-r0 do_package_qa: QA Issue: /usr/lib/expat/ptest/run-ptest contained in package expat-ptest requires /bin/bash, but no providers found in RDEPENDS:expat-ptest? [file-rdeps]
RDEPENDS:${PN}-ptest:append:class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS:${PN}-ptest:remove:class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"
