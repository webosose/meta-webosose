# Copyright (c) 2025 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos1"

# http://gecko.lge.com:8000/Errors/Details/1219896
# ERROR: QA Issue: libpng-ptest rdepends on bash, but it isn't a build dependency, missing bash in DEPENDS or PACKAGECONFIG? [build-deps]
VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS:${PN}-ptest:append:class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS:${PN}-ptest:remove:class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"
