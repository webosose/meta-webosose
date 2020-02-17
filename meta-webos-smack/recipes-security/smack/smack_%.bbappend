# Copyright (c) 2019-2020 LG Electronics, Inc.

EXTENDPRAUTO_append = "smack4"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += "file://default-access \
    file://unconfined \
    file://smack_rules_gen \
    file://onlycap \
"

VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS_${PN}-ptest_append_class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS_${PN}-ptest_remove_class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"

do_install_append (){
    install -d ${D}${sysconfdir}/smack/netlabel.d
    install -v -m 0644 ${WORKDIR}/default-access ${D}${sysconfdir}/smack/accesses.d/
    install -v -m 0644 ${WORKDIR}/unconfined ${D}${sysconfdir}/smack/
    install -v -m 0644 ${WORKDIR}/onlycap ${D}${sysconfdir}/smack/
    install -d ${D}${datadir}/smack
    install -v -m 0755 ${WORKDIR}/smack_rules_gen ${D}${datadir}/smack/smack_rules_gen
}

RDEPENDS_${PN} += "python3-shell"
