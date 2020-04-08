# Copyright (c) 2013-2020 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos23"

inherit update-alternatives
ALTERNATIVE_${PN}-conf = "openssl-cnf2"
ALTERNATIVE_LINK_NAME[openssl-cnf2] = "${sysconfdir}/ssl/openssl.cnf"
ALTERNATIVE_PRIORITY[openssl-cnf2] ?= "1"

do_install_append () {
    mv ${D}${sysconfdir}/ssl/openssl.cnf ${D}${sysconfdir}/ssl/openssl.cnf.${BPN}
}

FILES_openssl-conf = "${sysconfdir}/ssl/openssl.cnf*"

VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS_${PN}-ptest_append_class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS_${PN}-ptest_remove_class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"
