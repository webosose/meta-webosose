# Copyright (c) 2019-2020 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos3"

# ERROR: QA Issue: /usr/lib/elfutils/ptest/tests/run-ar.sh contained in package elfutils-ptest requires /bin/bash, but no providers found in RDEPENDS_elfutils-ptest? [file-rdeps]
VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS_${PN}-ptest_append_class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS_${PN}-ptest_remove_class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"
