# Copyright (c) 2017-2021 LG Electronics, Inc.

EXTENDPRAUTO_append = "webosrpi10"

do_deploy_append() {
    echo "gpu_mem=300" >> ${DEPLOYDIR}/${BOOTFILES_DIR_NAME}/config.txt
    echo "dtparam=audio=on" >> ${DEPLOYDIR}/${BOOTFILES_DIR_NAME}/config.txt
}

do_deploy_append_raspberrypi4() {
    echo >> ${DEPLOYDIR}/${BOOTFILES_DIR_NAME}/config.txt
    echo "[pi4]" >> ${DEPLOYDIR}/${BOOTFILES_DIR_NAME}/config.txt
    echo "max_framebuffers=2" >> ${DEPLOYDIR}/${BOOTFILES_DIR_NAME}/config.txt
    echo "# Enable I2C" >>${DEPLOYDIR}/${BOOTFILES_DIR_NAME}/config.txt
    echo "dtparam=i2c1=on" >>${DEPLOYDIR}/${BOOTFILES_DIR_NAME}/config.txt
    echo "dtparam=i2c_arm=on" >>${DEPLOYDIR}/${BOOTFILES_DIR_NAME}/config.txt
    echo "# Enable spi" >>${DEPLOYDIR}/${BOOTFILES_DIR_NAME}/config.txt
    echo "dtparam=spi=on" >>${DEPLOYDIR}/${BOOTFILES_DIR_NAME}/config.txt
}
