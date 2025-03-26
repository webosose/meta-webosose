# Copyright (c) 2023-2025 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos1"

# ERROR: QA Issue: lib32-etcd-dev rdepends on lib32-bash, but it isn't a build dependency, missing lib32-bash in DEPENDS or PACKAGECONFIG? [build-deps]
VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS:${PN}-dev:append:class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS:${PN}-dev:remove:class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"
