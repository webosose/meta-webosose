# Copyright (c) 2019-2023 LG Electronics, Inc.

EXTENDPRAUTO:append = "webosrpi4"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append = " \
    file://boot.sota.cmd \
    file://uEnv.sota.txt.in \
"

do_compile:sota() {
    sed -e 's/@@KERNEL_BOOTCMD@@/${KERNEL_BOOTCMD}/' "${WORKDIR}/uEnv.sota.txt.in" > "${WORKDIR}/uEnv.sota.txt"
    mkimage -A arm -O linux -T script -C none -a 0 -e 0 -n "Ostree boot script" -d ${WORKDIR}/boot.sota.cmd boot.scr
}

do_deploy:append:sota() {
    install -d ${DEPLOYDIR}/${BOOTFILES_DIR_NAME}
    install -m 0755 ${WORKDIR}/uEnv.sota.txt ${DEPLOYDIR}/${BOOTFILES_DIR_NAME}/uEnv.txt
}
