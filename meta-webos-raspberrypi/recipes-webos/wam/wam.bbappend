# Copyright (c) 2019 LG Electronics, Inc.

EXTENDPRAUTO_append_rpi = "webosrpi"

do_configure_append() {
    sed -i '/webapp-mgr\.env/a\# for using broadcom video hw codec\nExecStartPre=-\/bin\/chmod 777 \/dev\/vchiq' ${S}/files/launch/systemd/webapp-mgr.service
    sed -i 's/# setup 4 Mb limitation mse audio buffer size/# setup 2 Mb limitation mse audio buffer size/' ${S}/files/launch/systemd/webapp-mgr.sh.in
    sed -i 's/export MSE_AUDIO_BUFFER_SIZE_LIMIT.*/export MSE_AUDIO_BUFFER_SIZE_LIMIT=2097152/' ${S}/files/launch/systemd/webapp-mgr.sh.in
    sed -i 's/# setup 50 Mb limitation mse video buffer size/# setup 15 Mb limitation mse video buffer size/' ${S}/files/launch/systemd/webapp-mgr.sh.in
    sed -i 's/export MSE_VIDEO_BUFFER_SIZE_LIMIT.*/export MSE_VIDEO_BUFFER_SIZE_LIMIT=15728640/' ${S}/files/launch/systemd/webapp-mgr.sh.in
}
