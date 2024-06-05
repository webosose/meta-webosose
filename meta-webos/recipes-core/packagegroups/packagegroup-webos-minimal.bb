# Copyright (c) 2023-2024 LG Electronics, Inc.

DESCRIPTION = "Packages used by all distro variants of webOS OSE"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

PR = "r1"

inherit packagegroup

VIRTUAL-RUNTIME_browser_fonts ?= "webos-fonts"
VIRTUAL-RUNTIME_surface-manager ?= "luna-surfacemanager-base"
VIRTUAL-RUNTIME_surface-manager-conf ?= "luna-surfacemanager-conf"
VIRTUAL-RUNTIME_surface-manager-extension ?= ""
VIRTUAL-RUNTIME_webappmanager ?= ""
VIRTUAL-RUNTIME_webos-ime ?= ""

RDEPENDS:${PN} = " \
    fontconfig-utils \
    ${VIRTUAL-RUNTIME_browser_fonts} \
    ${VIRTUAL-RUNTIME_surface-manager} \
    ${VIRTUAL-RUNTIME_surface-manager-conf} \
    ${VIRTUAL-RUNTIME_surface-manager-extension} \
    ${VIRTUAL-RUNTIME_webappmanager} \
    ${VIRTUAL-RUNTIME_webos-ime} \
"
