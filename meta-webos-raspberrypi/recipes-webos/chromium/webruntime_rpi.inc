# Copyright (c) 2023 LG Electronics, Inc.

EXTENDPRAUTO:append:rpi = "webosrpi5"

modify_run_app_shell:append:rpi() {
     sed -i 's/# Setup 4Mb limitation mse audio buffer size/# Setup 2Mb limitation mse audio buffer size/' ${D}${APP_SHELL_RUNTIME_RUN_SCRIPT}
     sed -i 's/export MSE_AUDIO_BUFFER_SIZE_LIMIT=4194304/export MSE_AUDIO_BUFFER_SIZE_LIMIT=2097152/' ${D}${APP_SHELL_RUNTIME_RUN_SCRIPT}
     sed -i 's/# Setup 50Mb limitation mse video buffer size/# Setup 15Mb limitation mse video buffer size/' ${D}${APP_SHELL_RUNTIME_RUN_SCRIPT}
     sed -i 's/export MSE_VIDEO_BUFFER_SIZE_LIMIT=52428800/export MSE_VIDEO_BUFFER_SIZE_LIMIT=15728640/' ${D}${APP_SHELL_RUNTIME_RUN_SCRIPT}
}
