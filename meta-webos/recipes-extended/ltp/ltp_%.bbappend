# Copyright (c) 2017-2022 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos4"

VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS:${PN}:append:class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS:${PN}:remove:class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"

VIRTUAL-RUNTIME_tar ?= "tar"
RDEPENDS:${PN}:append:class-target = " ${VIRTUAL-RUNTIME_tar}"
RDEPENDS:${PN}:remove:class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_TAR', 'busybox', 'tar', '', d)}"

inherit webos_filesystem_paths

do_install:append() {
    if [ "${webos_optdir}" != "/opt" ] ; then
        install -d ${D}${webos_optdir}
        mv ${D}/opt/* ${D}${webos_optdir}/
        rmdir ${D}/opt
    fi
}

FILES:${PN} += "${webos_optdir}"
