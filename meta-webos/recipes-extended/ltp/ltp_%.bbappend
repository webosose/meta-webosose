# Copyright (c) 2017-2019 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos3"

VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS_${PN}_append_class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS_${PN}_remove_class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"

inherit webos_filesystem_paths

do_install_append() {
    if [ "${webos_optdir}" != "/opt" ] ; then
        install -d ${D}${webos_optdir}
        mv ${D}/opt/* ${D}${webos_optdir}/
        rmdir ${D}/opt
    fi
}

FILES_${PN} += "${webos_optdir}"
