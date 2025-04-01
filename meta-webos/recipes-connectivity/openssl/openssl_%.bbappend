# Copyright (c) 2013-2025 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos25"

inherit update-alternatives
ALTERNATIVE:${PN}-conf = "openssl-cnf2"
ALTERNATIVE_LINK_NAME[openssl-cnf2] = "${sysconfdir}/ssl/openssl.cnf"
ALTERNATIVE_PRIORITY[openssl-cnf2] ?= "1"

do_install:append:class-target() {
    mv ${D}${sysconfdir}/ssl/openssl.cnf ${D}${sysconfdir}/ssl/openssl.cnf.${BPN}
}

FILES:openssl-conf = "${sysconfdir}/ssl/openssl.cnf*"

VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS:${PN}-ptest:append:class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS:${PN}-ptest:remove:class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"

FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

SRC_URI += " \
    file://0471-Coverity-1528492-Fix-possible-memory-leak-if-t-NULL.patch \
    file://0472-Fix-a-mem-leak-when-the-FIPS-provider-is-used-in-a-d.patch \
"
