# Copyright (c) 2017-2019 LG Electronics, Inc.

EXTENDPRAUTO_append = "webosrpi5"

do_deploy_append() {
    echo "gpu_mem=300" >> ${DEPLOYDIR}/bcm2835-bootfiles/config.txt
    echo "dtparam=audio=on" >> ${DEPLOYDIR}/bcm2835-bootfiles/config.txt
}
