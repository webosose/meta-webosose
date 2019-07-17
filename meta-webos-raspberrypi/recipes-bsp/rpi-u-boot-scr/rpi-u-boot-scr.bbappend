# Copyright (c) 2019 LG Electronics, Inc.

EXTENDPRAUTO_append = "webosrpi1"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI_append = " \
    file://boot.sota.cmd \
    file://uEnv.sota.txt.in \
"

do_compile_sota() {
    sed -e 's/@@KERNEL_BOOTCMD@@/${KERNEL_BOOTCMD}/' "${WORKDIR}/uEnv.sota.txt.in" > "${WORKDIR}/uEnv.sota.txt"
    mkimage -A arm -O linux -T script -C none -a 0 -e 0 -n "Ostree boot script" -d ${WORKDIR}/boot.sota.cmd boot.scr
}

do_deploy_append_sota() {
    install -d ${DEPLOYDIR}/bcm2835-bootfiles
    install -m 0755 ${WORKDIR}/uEnv.sota.txt ${DEPLOYDIR}/bcm2835-bootfiles/uEnv.txt
}
