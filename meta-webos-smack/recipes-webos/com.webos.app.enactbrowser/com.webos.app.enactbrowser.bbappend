# Copyright (c) 2019-2024 LG Electronics, Inc.

EXTENDPRAUTO:append = "${@bb.utils.contains('DISTRO_FEATURES', 'smack', 'smack2', '', d)}"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += "\
    ${@bb.utils.contains('DISTRO_FEATURES', 'smack', 'file://com.webos.app.enactbrowser', '', d)} \
"

do_install[postfuncs] += "${@bb.utils.contains('DISTRO_FEATURES', 'smack', 'install_smack_rules', '', d)}"

install_smack_rules (){
    install -d ${D}${sysconfdir}/smack/accesses.d
    install -v -m 0644 ${WORKDIR}/com.webos.app.enactbrowser ${D}${sysconfdir}/smack/accesses.d/com.webos.app.enactbrowser
}
