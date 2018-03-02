# Copyright (c) 2017 LG Electronics, Inc.

EXTENDPRAUTO_append = "webosrpi2"

ENABLE_UART = "1"

do_deploy_append() {
    echo "dtoverlay=pi3-miniuart-bt" >> ${DEPLOYDIR}/bcm2835-bootfiles/config.txt
    echo "dtparam=audio=on" >> ${DEPLOYDIR}/bcm2835-bootfiles/config.txt
}
