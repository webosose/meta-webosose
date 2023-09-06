# Copyright (c) 2017-2023 LG Electronics, Inc.

EXTENDPRAUTO:append:rpi = "webosrpi12"

VIRTUAL-RUNTIME_com.webos.service.cec ?= ""
# CEC service functionality is supported only for webOS OSE rpi4-64
VIRTUAL-RUNTIME_com.webos.service.cec:raspberrypi4-64 = " \
    com.webos.service.cec \
"

VIRTUAL-RUNTIME_part-initializer ?= ""
VIRTUAL-RUNTIME_part-initializer:rpi = "setup-partitions"

RDEPENDS:${PN}:append:rpi = " \
    alsa-utils \
    boot-verifier \
    com.webos.service.audiofocusmanager \
    com.webos.service.audiooutput \
    com.webos.service.hfp \
    com.webos.service.location \
    com.webos.service.mediaindexer \
    com.webos.service.peripheralmanager \
    com.webos.service.power2 \
    libbootctrl-tests \
    ofono \
    ${VIRTUAL-RUNTIME_com.webos.service.cec} \
    ${VIRTUAL-RUNTIME_part-initializer} \
"
