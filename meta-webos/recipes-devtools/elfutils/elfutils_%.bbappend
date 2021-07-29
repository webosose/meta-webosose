# Copyright (c) 2019-2020 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos3"

# ERROR: QA Issue: /usr/lib/elfutils/ptest/tests/run-ar.sh contained in package elfutils-ptest requires /bin/bash, but no providers found in RDEPENDS:elfutils-ptest? [file-rdeps]
VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS:${PN}-ptest:append:class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS:${PN}-ptest:remove:class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"
