# Copyright (c) 2018 LG Electronics, Inc.

EXTENDPRAUTO_append_rpi = "webosrpi1"

install_app_shell_append() {
     sed -i 's/# Setup 4Mb limitation mse audio buffer size/# Setup 2Mb limitation mse audio buffer size/' ${A_DIR}/run_app_shell
     sed -i 's/export MSE_AUDIO_BUFFER_SIZE_LIMIT=4194304/export MSE_AUDIO_BUFFER_SIZE_LIMIT=2097152/' ${A_DIR}/run_app_shell
     sed -i 's/# Setup 50Mb limitation mse video buffer size/# Setup 15Mb limitation mse video buffer size/' ${A_DIR}/run_app_shell
     sed -i 's/export MSE_VIDEO_BUFFER_SIZE_LIMIT=52428800/export MSE_VIDEO_BUFFER_SIZE_LIMIT=15728640/' ${A_DIR}/run_app_shell
}
