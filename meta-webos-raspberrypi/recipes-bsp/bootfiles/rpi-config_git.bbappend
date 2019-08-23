# Copyright (c) 2017-2019 LG Electronics, Inc.

EXTENDPRAUTO_append = "webosrpi7"

do_deploy_append() {
    echo "gpu_mem=300" >> ${DEPLOYDIR}/bcm2835-bootfiles/config.txt
    echo "dtparam=audio=on" >> ${DEPLOYDIR}/bcm2835-bootfiles/config.txt
}

do_deploy_append_raspberrypi4() {
    echo -e "\n[pi4]" >> ${DEPLOYDIR}/bcm2835-bootfiles/config.txt
    echo "max_framebuffers=2" >> ${DEPLOYDIR}/bcm2835-bootfiles/config.txt
}
