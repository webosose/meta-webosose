# Copyright (c) 2019-2024 LG Electronics, Inc.

EXTENDPRAUTO:append = "webosrpi6"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

UENV_FILE = "uEnv.txt.in"

# boot.cmd.in is in SRC_URI already and copied under 'files' of this dir
SRC_URI:append = " \
    file://${UENV_FILE} \
"

do_compile:append() {
    sed -e 's/@@KERNEL_IMAGETYPE@@/${KERNEL_IMAGETYPE}/' \
        -e 's/@@KERNEL_BOOTCMD@@/${KERNEL_BOOTCMD}/' \
        "${WORKDIR}/${UENV_FILE}" > "${WORKDIR}/uEnv.txt"
}

do_deploy:append() {
    install -d ${DEPLOYDIR}/${BOOTFILES_DIR_NAME}
    install -m 0755 ${WORKDIR}/uEnv.txt ${DEPLOYDIR}/${BOOTFILES_DIR_NAME}
}
