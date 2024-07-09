# Copyright (c) 2023-2024 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos4"

VIRTUAL-RUNTIME_initscripts ?= "initscripts"
VIRTUAL-RUNTIME_nyx_modules_providers ??= " \
    nyx-modules \
    nyx-modules-qemux86 \
"
VIRTUAL-RUNTIME_pdm ?= "com.webos.service.pdm"

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
    kernel \
    kernel-base \
    kernel-image \
    lsb-release \
    procps \
    ${@bb.utils.contains('DISTRO_FEATURES', 'webos-essential', '${WEBOS_ESSENTIAL_PACKAGES}', '', d)} \
"
