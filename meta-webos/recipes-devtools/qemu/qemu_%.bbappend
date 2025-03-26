# Copyright (c) 2017-2025 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos5"

VIRTUAL-RUNTIME_bash ?= "bash"
# ERROR: QA Issue: qemu-common rdepends on bash, but it isn't a build dependency, missing bash in DEPENDS or PACKAGECONFIG? [build-deps]
# http://gecko.lge.com:8000/Errors/Details/665143
RDEPENDS:${PN}-common:append:class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS:${PN}-common:remove:class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"
RDEPENDS:${PN}-ptest:append:class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS:${PN}-ptest:remove:class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"

# ERROR: qemu-8.0.0-r0 do_package_qa: QA Issue: qemu-user-mips rdepends on bash, but it isn't a build dependency, missing bash in DEPENDS or PACKAGECONFIG? [build-deps]
# http://gecko.lge.com:8000/Errors/Details/617757
RDEPENDS:${PN}-user-mips:append:class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS:${PN}-user-mips:remove:class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"

# ERROR: QA Issue: nativesdk-qemu-user-mips rdepends on nativesdk-bash, but it isn't a build dependency, missing nativesdk-bash in DEPENDS or PACKAGECONFIG? [build-deps]
# http://gecko.lge.com:8000/Errors/Details/930485
# this one is difficult to resolve, it was later removed in styhead by:
# https://git.openembedded.org/openembedded-core/commit/?id=b30c1e5805b3f108a2d0a30259b50b9e7db0f6cc
# lets ignore it for now as we don't use mips anyway
INSANE_SKIP:${PN}-user-mips += "build-deps"
