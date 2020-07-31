# Copyright (c) 2017-2020 LG Electronics, Inc.

EXTENDPRAUTO_append = "webosrpi9"

do_deploy_append() {
    echo "gpu_mem=300" >> ${DEPLOYDIR}/${BOOTFILES_DIR_NAME}/config.txt
    echo "dtparam=audio=on" >> ${DEPLOYDIR}/${BOOTFILES_DIR_NAME}/config.txt
}

do_deploy_append_raspberrypi4() {
    echo >> ${DEPLOYDIR}/${BOOTFILES_DIR_NAME}/config.txt
    echo "[pi4]" >> ${DEPLOYDIR}/${BOOTFILES_DIR_NAME}/config.txt
    echo "max_framebuffers=2" >> ${DEPLOYDIR}/${BOOTFILES_DIR_NAME}/config.txt
}
