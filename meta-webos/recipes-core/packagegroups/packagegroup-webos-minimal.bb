# Copyright (c) 2023-2025 LG Electronics, Inc.

DESCRIPTION = "Packages used by all distro variants of webOS OSE"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

PR = "r4"

inherit packagegroup
inherit features_check
ANY_OF_DISTRO_FEATURES = "webos-graphics webos-graphics-extended"

VIRTUAL-RUNTIME_browser_fonts ?= "webos-fonts"
VIRTUAL-RUNTIME_surface-manager ?= "${@bb.utils.contains('DISTRO_FEATURES', 'webos-qt', 'luna-surfacemanager-base', '', d)}"
VIRTUAL-RUNTIME_surface-manager-conf ?= "${@bb.utils.contains('DISTRO_FEATURES', 'webos-qt', 'luna-surfacemanager-conf', '', d)}"
VIRTUAL-RUNTIME_surface-manager-extension ?= ""
VIRTUAL-RUNTIME_webappmanager ?= ""
VIRTUAL-RUNTIME_webos-ime ?= ""

RDEPENDS:${PN} = " \
    fontconfig-utils \
    packagegroup-webos-graphics \
    ${VIRTUAL-RUNTIME_browser_fonts} \
    ${VIRTUAL-RUNTIME_webappmanager} \
    ${VIRTUAL-RUNTIME_webos-ime} \
"
