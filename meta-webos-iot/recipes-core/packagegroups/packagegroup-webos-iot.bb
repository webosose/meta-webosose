# Copyright (c) 2020-2022 LG Electronics, Inc.

DESCRIPTION = "meta-webos-iot components used in webOS OSE"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

# You don't need to change this value when you're changing just RDEPENDS:${PN} variable.
PR = "r1"

PACKAGE_ARCH = "${MACHINE_ARCH}"
inherit packagegroup
inherit webos_machine_impl_dep

# VIRTUAL-RUNTIME_ai : copied from meta-webos
VIRTUAL-RUNTIME_ai ?= ""
VIRTUAL-RUNTIME_ai:raspberrypi3 = "com.webos.service.ai"
VIRTUAL-RUNTIME_ai:raspberrypi4 = "com.webos.service.ai"
# There is only rpi-32bit keyword detection library available.(https://github.com/Kitt-AI/snowboy/tree/master/lib)
# It seems to be a library for arm-64bit(https://github.com/Kitt-AI/snowboy/tree/master/lib/aarch64-ubuntu1604),
# but it has not been verified on webOS rpi64 which cannot boot yet.
VIRTUAL-RUNTIME_ai:raspberrypi3-64 = ""
VIRTUAL-RUNTIME_ai:raspberrypi4-64 = ""

VIRTUAL-RUNTIME_com.example.service.iotivity ?= "com.example.service.iotivity"
VIRTUAL-RUNTIME_com.example.service.iotivity:armv4 = ""
VIRTUAL-RUNTIME_com.example.service.iotivity:armv5 = ""

VIRTUAL-RUNTIME_org.ocf.webossample ?= "org.ocf.webossample.occlientbasicops org.ocf.webossample.ocserverbasicops"
VIRTUAL-RUNTIME_org.ocf.webossample:armv4 = ""
VIRTUAL-RUNTIME_org.ocf.webossample:armv5 = ""

RDEPENDS:${PN} = " \
    activitymanager \
    audiod \
    bootd \
    configurator \
    iotivity \
    luna-downloadmgr \
    luna-init \
    luna-sysservice \
    mojoservicelauncher \
    nodejs-module-webos-service \
    procps \
    rng-tools \
    webos-connman-adapter \
    ${VIRTUAL-RUNTIME_ai} \
    ${VIRTUAL-RUNTIME_com.example.service.iotivity} \
    ${VIRTUAL-RUNTIME_org.ocf.webossample} \
"
