# Copyright (c) 2019-2020 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos2"

# ERROR: QA Issue: /usr/lib/elfutils/ptest/tests/run-ar.sh contained in package elfutils-ptest requires /bin/bash, but no providers found in RDEPENDS_elfutils-ptest? [file-rdeps]
VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS_${PN}-ptest_append_class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS_${PN}-ptest_remove_class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

# PLAT-79213: Fix an error in do_configure with -Werror=return-type
SRC_URI += "file://fix-configure.patch"
