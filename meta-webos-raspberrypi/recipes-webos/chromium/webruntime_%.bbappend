# Copyright (c) 2018-2020 LG Electronics, Inc.

EXTENDPRAUTO_append_rpi = "webosrpi9"

modify_run_app_shell_append_rpi() {
     sed -i 's/# Setup 4Mb limitation mse audio buffer size/# Setup 2Mb limitation mse audio buffer size/' ${D}${APP_SHELL_RUNTIME_RUN_SCRIPT}
     sed -i 's/export MSE_AUDIO_BUFFER_SIZE_LIMIT=4194304/export MSE_AUDIO_BUFFER_SIZE_LIMIT=2097152/' ${D}${APP_SHELL_RUNTIME_RUN_SCRIPT}
     sed -i 's/# Setup 50Mb limitation mse video buffer size/# Setup 15Mb limitation mse video buffer size/' ${D}${APP_SHELL_RUNTIME_RUN_SCRIPT}
     sed -i 's/export MSE_VIDEO_BUFFER_SIZE_LIMIT=52428800/export MSE_VIDEO_BUFFER_SIZE_LIMIT=15728640/' ${D}${APP_SHELL_RUNTIME_RUN_SCRIPT}
}

PACKAGECONFIG_remove_raspberrypi3 = "gstreamer umediaserver neva-media gav"
PACKAGECONFIG_remove_raspberrypi3-64 = "gstreamer umediaserver neva-media gav"

PACKAGECONFIG_NEVA_WEBRTC ?= "neva-webrtc"
PACKAGECONFIG_append_raspberrypi4 = " ${PACKAGECONFIG_NEVA_WEBRTC}"
