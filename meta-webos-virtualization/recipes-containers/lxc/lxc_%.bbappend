# Copyright (c) 2020-2021 LG Electronics, Inc.

EXTENDPRAUTO:append = "webosvirt3"

# ERROR: lxc-3.1.0-r0 do_package_qa: QA Issue: lxc-ptest rdepends on bash, but it isnt a build dependency, missing bash in DEPENDS or PACKAGECONFIG? [build-deps]
VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS:${PN}-ptest:append:class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS:${PN}-ptest:remove:class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"

# ERROR: lxc-3.1.0-r0 do_package_qa: QA Issue: lxc-templates rdepends on bash, but it isn't a build dependency, missing bash in DEPENDS or PACKAGECONFIG? [build-deps]
RDEPENDS:${PN}-templates:append:class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS:${PN}-templates:remove:class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"

VIRTUAL-RUNTIME_tar ?= "tar"
RDEPENDS:${PN}:append:class-target = " ${VIRTUAL-RUNTIME_tar}"
RDEPENDS:${PN}:remove:class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_TAR', 'busybox', 'tar', '', d)}"
