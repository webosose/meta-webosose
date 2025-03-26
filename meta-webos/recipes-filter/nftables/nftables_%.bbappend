# Copyright (c) 2023-2025 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos1"

# ERROR: QA Issue: nftables-ptest rdepends on bash, but it isn't a build dependency, missing bash in DEPENDS or PACKAGECONFIG? [build-deps]
# http://gecko.lge.com:8000/Errors/Details/564867
VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS:${PN}-ptest:append:class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS:${PN}-ptest:remove:class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"
