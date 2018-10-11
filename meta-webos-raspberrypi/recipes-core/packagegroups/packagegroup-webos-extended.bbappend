# Copyright (c) 2017-2019 LG Electronics, Inc.

# You don't need to change this value when you're changing just a RDEPENDS_${PN} variable.
EXTENDPRAUTO_append_rpi = "webosrpi6"

# gst omx is used only for raspberrypi3 builds
MEDIA_append_rpi = " \
    gstreamer1.0-omx \
"
# Until build issues caused by PLAT-44962 are fixed in PLAT-45700
MEDIA_raspberrypi3-64 = ""

AISERVICE = " \
    com.webos.service.ai \
"

TTSSERVICE = " \
    com.webos.service.tts \
"

CIMSERVICE = " \
    com.webos.service.contextintentmgr \
"
# The same restrition as nodejs (and nodejs-module-node-red and com.webos.service.contextintentmgr)
CIMSERVICE_armv4 = ""
CIMSERVICE_armv5 = ""
CIMSERVICE_mips64 = ""

PDM = " \
    com.webos.service.pdm \
"

CAMERASERVICE = " \
    com.webos.service.camera \
    g-camera-pipeline \
"

# There is only rpi-32bit keyword detection library available.(https://github.com/Kitt-AI/snowboy/tree/master/lib)
# It seems to be a library for arm-64bit(https://github.com/Kitt-AI/snowboy/tree/master/lib/aarch64-ubuntu1604),
# but it has not been verified on webOS rpi64 which cannot boot yet.
AISERVICE_raspberrypi3-64 = ""

# TTS service functionality cannot be verified on webOS rpi64 which cannot boot yet
TTSSERVICE_raspberrypi3-64 = ""

# CIM service functionality not been verified on webOS rpi64 which cannot boot yet
CIMSERVICE_raspberrypi3-64 = ""

# PDM service functionality cannot be verified on webOS rpi64 which cannot boot yet
PDM_raspberrypi3-64 = ""

# camera service functionality cannot be verified on webOS rpi64 which cannot boot yet
CAMERASERVICE_raspberrypi3-64 = ""

RDEPENDS_${PN}_append_rpi = " \
    com.webos.service.audiooutput \
    ${AISERVICE} \
    ${CAMERASERVICE} \
    ${TTSSERVICE} \
    ${CIMSERVICE} \
    ${PDM} \
"
