# Copyright (c) 2023-2024 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos1"

# ERROR: QA Issue: gosu-dev rdepends on bash, but it isn't a build dependency, missing bash in DEPENDS or PACKAGECONFIG? [build-deps]
# http://gecko.lge.com:8000/Errors/Details/564885
VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS:${PN}-dev:append:class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS:${PN}-dev:remove:class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"
