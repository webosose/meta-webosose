# Copyright (c) 2019-2024 LG Electronics, Inc.

EXTENDPRAUTO:append:rpi = "webosrpi8"

FILESEXTRAPATHS:prepend:rpi := "${THISDIR}/${BPN}:"

UENV_FILE:rpi = "uEnv.txt.in"

# boot.cmd.in in SRC_URI was already defined at meta-raspberrypi and copied under '${BPN}' of this dir
SRC_URI:append:rpi = " \
    file://${UENV_FILE} \
"

do_compile:append:rpi () {
    sed -e 's/@@KERNEL_IMAGETYPE@@/${KERNEL_IMAGETYPE}/' \
        -e 's/@@KERNEL_BOOTCMD@@/${KERNEL_BOOTCMD}/' \
        "${UNPACKDIR}/${UENV_FILE}" > "${WORKDIR}/uEnv.txt"
}

do_deploy:append:rpi () {
    install -d ${DEPLOYDIR}/${BOOTFILES_DIR_NAME}
    install -m 0755 ${WORKDIR}/uEnv.txt ${DEPLOYDIR}/${BOOTFILES_DIR_NAME}
}
