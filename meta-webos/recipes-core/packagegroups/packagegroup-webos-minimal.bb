# Copyright (c) 2023 LG Electronics, Inc.

DESCRIPTION = "Packages used by all distro variants of webOS OSE"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

PR = "r0"

inherit packagegroup

VIRTUAL-RUNTIME_browser_fonts ?= "webos-fonts"
VIRTUAL-RUNTIME_initscripts ?= "initscripts"
VIRTUAL-RUNTIME_nyx_modules_providers ??= " \
    nyx-modules \
    nyx-modules-qemux86 \
"
VIRTUAL-RUNTIME_surface-manager ?= "luna-surfacemanager-base"
VIRTUAL-RUNTIME_surface-manager-conf ?= "luna-surfacemanager-conf"
VIRTUAL-RUNTIME_surface-manager-extension ?= ""
VIRTUAL-RUNTIME_webappmanager ?= ""
VIRTUAL-RUNTIME_webos-ime ?= ""

RDEPENDS:${PN} = " \
    bootd \
    configd \
    fontconfig-utils \
    luna-sysservice \
    makedevs \
    pmlogctl \
    sam \
    settingsservice \
    webos-connman-adapter \
    ${VIRTUAL-RUNTIME_browser_fonts} \
    ${VIRTUAL-RUNTIME_initscripts} \
    ${VIRTUAL-RUNTIME_nyx_modules_providers} \
    ${VIRTUAL-RUNTIME_surface-manager} \
    ${VIRTUAL-RUNTIME_surface-manager-conf} \
    ${VIRTUAL-RUNTIME_surface-manager-extension} \
    ${VIRTUAL-RUNTIME_webappmanager} \
    ${VIRTUAL-RUNTIME_webos-ime} \
"

# Add to lg/mstar/rtk target only
# FIXME: In TV SoCs like O22, USB becomes functional only after PDM does some magic with /proc and GPIOs.
require conf/machine/include/soc-family.inc
VIRTUAL-RUNTIME_pdm ?= "com.webos.service.pdm"
RDEPENDS:${PN}:append:lg1 = " ${VIRTUAL-RUNTIME_pdm}"
RDEPENDS:${PN}:append:mstar1 = " ${VIRTUAL-RUNTIME_pdm}"
RDEPENDS:${PN}:append:rtk1 = " ${VIRTUAL-RUNTIME_pdm}"
