# Copyright (c) 2020 LG Electronics, Inc.

DESCRIPTION = "meta-webos-iot components used in webOS OSE"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

# You don't need to change this value when you're changing just RDEPENDS_${PN} variable.
PR = "r0"

PACKAGE_ARCH = "${MACHINE_ARCH}"
inherit packagegroup
inherit webos_machine_impl_dep

# VIRTUAL-RUNTIME_ai : copied from meta-webos
VIRTUAL-RUNTIME_ai ?= ""
VIRTUAL-RUNTIME_ai_raspberrypi3 = "com.webos.service.ai"
VIRTUAL-RUNTIME_ai_raspberrypi4 = "com.webos.service.ai"
# There is only rpi-32bit keyword detection library available.(https://github.com/Kitt-AI/snowboy/tree/master/lib)
# It seems to be a library for arm-64bit(https://github.com/Kitt-AI/snowboy/tree/master/lib/aarch64-ubuntu1604),
# but it has not been verified on webOS rpi64 which cannot boot yet.
VIRTUAL-RUNTIME_ai_raspberrypi3-64 = ""
VIRTUAL-RUNTIME_ai_raspberrypi4-64 = ""

VIRTUAL-RUNTIME_com.example.service.iotivity ?= "com.example.service.iotivity"
VIRTUAL-RUNTIME_com.example.service.iotivity_armv4 = ""
VIRTUAL-RUNTIME_com.example.service.iotivity_armv5 = ""

RDEPENDS_${PN} = " \
    bootd \
    webos-connman-adapter \
    iotivity \
    org.ocf.webossample.ocserverbasicops \
    org.ocf.webossample.occlientbasicops \
    procps \
    ${VIRTUAL-RUNTIME_ai} \
    audiod \
    rng-tools \
    ${VIRTUAL-RUNTIME_com.example.service.iotivity} \
    mojoservicelauncher \
    nodejs-module-webos-service \
    iotivity-node \
    activitymanager \
    configurator \
    luna-sysservice \
    luna-init \
    luna-downloadmgr \
"
