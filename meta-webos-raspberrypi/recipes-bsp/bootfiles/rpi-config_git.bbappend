# Copyright (c) 2017-2018 LG Electronics, Inc.

EXTENDPRAUTO_append = "webosrpi4"

do_deploy_append() {
    echo "dtparam=audio=on" >> ${DEPLOYDIR}/bcm2835-bootfiles/config.txt
}
