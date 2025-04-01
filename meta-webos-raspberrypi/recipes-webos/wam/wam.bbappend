# Copyright (c) 2019-2025 LG Electronics, Inc.

EXTENDPRAUTO:append:rpi = "webosrpi5"

install_units:append:rpi() {
    service=${D}${systemd_system_unitdir}/webapp-mgr.service
    script=${D}${systemd_system_unitdir}/scripts/webapp-mgr.sh
    if [ -f "$service" ]; then
        sed -i '/webapp-mgr\.env/a\# for using broadcom video hw codec\nExecStartPre=-\/bin\/chmod 777 \/dev\/vchiq' ${service}
    fi
    if [ -f "$service" ]; then
        sed -i 's/# setup 4 Mb limitation mse audio buffer size/# setup 2 Mb limitation mse audio buffer size/' $script
        sed -i 's/export MSE_AUDIO_BUFFER_SIZE_LIMIT.*/export MSE_AUDIO_BUFFER_SIZE_LIMIT=2097152/' $script
        sed -i 's/# setup 50 Mb limitation mse video buffer size/# setup 15 Mb limitation mse video buffer size/' $script
        sed -i 's/export MSE_VIDEO_BUFFER_SIZE_LIMIT.*/export MSE_VIDEO_BUFFER_SIZE_LIMIT=15728640/' $script
    fi
}

