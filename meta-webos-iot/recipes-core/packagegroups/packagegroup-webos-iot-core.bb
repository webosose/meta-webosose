# Copyright (c) 2020-2021 LG Electronics, Inc.

DESCRIPTION = "meta-webos-iot components used in webOS OSE"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

# You don't need to change this value when you're changing just RDEPENDS_${PN} variable.
PR = "r0"

PACKAGE_ARCH = "${MACHINE_ARCH}"
inherit packagegroup
inherit webos_machine_impl_dep

VIRTUAL-RUNTIME_nyx_modules_providers ??= "\
    nyx-utils \
    nyx-modules \
    nyx-modules-qemux86 \
"

RDEPENDS_${PN} = " \
    pmklogd \
    pmlogctl \
    pmlogdaemon \
    ${VIRTUAL-RUNTIME_initscripts} \
    ${VIRTUAL-RUNTIME_nyx_modules_providers} \
    ${WEBOS_FOSS_MISSING_FROM_RDEPENDS} \
"

# XXX These FOSS components must be explicitly added because they are missing
# from the RDEPENDS lists of the components that expect them to be present at
# runtime (or perhaps some are in fact top-level components and some others
# aren't actually needed).
WEBOS_FOSS_MISSING_FROM_RDEPENDS = " \
    curl \
    openssl \
    ${@'systemd-analyze' if '${VIRTUAL-RUNTIME_init_manager}' == 'systemd' else 'sysvinit-pidof'} \
"

RDEPENDS_${PN} += "${MACHINE_EXTRA_RDEPENDS}"
RRECOMMENDS_${PN} += "${MACHINE_EXTRA_RRECOMMENDS}"

