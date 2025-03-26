# Copyright (c) 2020-2025 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos1"

VIRTUAL-RUNTIME_bash ?= "bash"
# ERROR: sshfs-fuse-3.7.0-r0 do_package_qa: QA Issue: sshfs-fuse-ptest rdepends on bash, but it isn't a build dependency, missing bash in DEPENDS or PACKAGECONFIG? [build-deps]
RDEPENDS:${PN}-ptest:append:class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS:${PN}-ptest:remove:class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"
