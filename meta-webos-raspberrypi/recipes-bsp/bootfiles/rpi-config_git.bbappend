# Copyright (c) 2017-2025 LG Electronics, Inc.

EXTENDPRAUTO:append:rpi = "webosrpi13"

do_deploy:append:rpi() {
    echo "dtparam=audio=on" >> ${CONFIG}
    echo >> ${CONFIG}
    echo "[pi4]" >> ${CONFIG}
    echo "max_framebuffers=2" >> ${CONFIG}
    if [ "${USE_MIC_ARRAY}" = "1" ]; then
        echo "# dtoverlay=seeed-4mic-voicecard" >> ${CONFIG}
        echo "# dtoverlay=seeed-8mic-voicecard" >> ${CONFIG}
        echo "dtparam=i2s=on" >> ${CONFIG}
    fi
}
