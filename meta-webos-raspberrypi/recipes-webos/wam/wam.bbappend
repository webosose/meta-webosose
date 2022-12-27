# Copyright (c) 2019-2023 LG Electronics, Inc.

EXTENDPRAUTO:append:rpi = "webosrpi4"

FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

do_configure:append() {
    sed -i '/webapp-mgr\.env/a\# for using broadcom video hw codec\nExecStartPre=-\/bin\/chmod 777 \/dev\/vchiq' ${B}/webapp-mgr.service
    sed -i 's/# setup 4 Mb limitation mse audio buffer size/# setup 2 Mb limitation mse audio buffer size/' ${B}/webapp-mgr.sh
    sed -i 's/export MSE_AUDIO_BUFFER_SIZE_LIMIT.*/export MSE_AUDIO_BUFFER_SIZE_LIMIT=2097152/' ${B}/webapp-mgr.sh
    sed -i 's/# setup 50 Mb limitation mse video buffer size/# setup 15 Mb limitation mse video buffer size/' ${B}/webapp-mgr.sh
    sed -i 's/export MSE_VIDEO_BUFFER_SIZE_LIMIT.*/export MSE_VIDEO_BUFFER_SIZE_LIMIT=15728640/' ${B}/webapp-mgr.sh
}

