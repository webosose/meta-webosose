# Copyright (c) 2023-2024 LG Electronics, Inc.

DESCRIPTION = "Packages used by all distro variants of webOS OSE"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

inherit packagegroup

PR = "r1"

VIRTUAL-RUNTIME_initscripts ?= "initscripts"
VIRTUAL-RUNTIME_nyx_modules_providers ??= " \
    nyx-modules \
    nyx-modules-qemux86 \
"
VIRTUAL-RUNTIME_pdm ?= "com.webos.service.pdm"

KERNEL_ESSENTIAL_PACKAGES = " \
    kernel \
    kernel-base \
    kernel-image \
"

WEBOS_ESSENTIAL_PACKAGES = " \
    activitymanager \
    bootd \
    configd \
    configurator \
    luna-sysservice \
    makedevs \
    pmlogctl \
    ${@bb.utils.filter('DISTRO_FEATURES', 'polkit', d)} \
    sam \
    settingsservice \
    webos-connman-adapter \
    ${VIRTUAL-RUNTIME_initscripts} \
    ${VIRTUAL-RUNTIME_nyx_modules_providers} \
    ${VIRTUAL-RUNTIME_pdm} \
"

RDEPENDS:${PN} += " \
    connman-client \
    lsb-release \
    packagegroup-core-boot \
    procps \
    ${KERNEL_ESSENTIAL_PACKAGES} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'webos-essential', '${WEBOS_ESSENTIAL_PACKAGES}', '', d)} \
"
