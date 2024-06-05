# Copyright (c) 2023-2024 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos2"

VIRTUAL-RUNTIME_initscripts ?= "initscripts"
VIRTUAL-RUNTIME_nyx_modules_providers ??= " \
    nyx-modules \
    nyx-modules-qemux86 \
"
VIRTUAL-RUNTIME_pdm ?= "com.webos.service.pdm"

RDEPENDS:${PN} += " \
    connman-client \
    kernel \
    kernel-base \
    kernel-image \
    lsb-release \
    procps \
    ${@bb.utils.contains('DISTRO_FEATURES', 'webos-essential', ' \
        bootd \
        configd \
        luna-sysservice \
        makedevs \
        pmlogctl \
        sam \
        settingsservice \
        webos-connman-adapter \
        ${VIRTUAL-RUNTIME_initscripts} \
        ${VIRTUAL-RUNTIME_nyx_modules_providers} \
        ${VIRTUAL-RUNTIME_pdm} \
    ', '', d)} \
"
