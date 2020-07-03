# Copyright (c) 2019-2020 LG Electronics, Inc.

EXTENDPRAUTO_append_rpi = "webosrpi3"

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

do_configure_append() {
    sed -i '/webapp-mgr\.env/a\# for using broadcom video hw codec\nExecStartPre=-\/bin\/chmod 777 \/dev\/vchiq' ${B}/webapp-mgr.service
    sed -i 's/# setup 4 Mb limitation mse audio buffer size/# setup 2 Mb limitation mse audio buffer size/' ${B}/webapp-mgr.sh
    sed -i 's/export MSE_AUDIO_BUFFER_SIZE_LIMIT.*/export MSE_AUDIO_BUFFER_SIZE_LIMIT=2097152/' ${B}/webapp-mgr.sh
    sed -i 's/# setup 50 Mb limitation mse video buffer size/# setup 15 Mb limitation mse video buffer size/' ${B}/webapp-mgr.sh
    sed -i 's/export MSE_VIDEO_BUFFER_SIZE_LIMIT.*/export MSE_VIDEO_BUFFER_SIZE_LIMIT=15728640/' ${B}/webapp-mgr.sh
}

SRC_URI_append = " file://0001-disable-neva-media-for-use-software-codec.patch"
