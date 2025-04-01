# Copyright (c) 2023-2025 LG Electronics, Inc.

DESCRIPTION = "Components for graphics added to webOS"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

PR = "r3"

inherit packagegroup
inherit features_check

ANY_OF_DISTRO_FEATURES = "webos-graphics webos-graphics-extended"

VIRTUAL-RUNTIME_surface-manager ?= "${@bb.utils.contains('DISTRO_FEATURES', 'webos-qt', 'luna-surfacemanager-base', '', d)}"
VIRTUAL-RUNTIME_surface-manager-conf ?= "${@bb.utils.contains('DISTRO_FEATURES', 'webos-qt', 'luna-surfacemanager-conf', '', d)}"
VIRTUAL-RUNTIME_surface-manager-extension ?= ""
VIRTUAL-RUNTIME_webappmanager ?= ""

RDEPENDS:${PN} = " \
    ${VIRTUAL-RUNTIME_surface-manager} \
    ${VIRTUAL-RUNTIME_surface-manager-conf} \
    ${VIRTUAL-RUNTIME_surface-manager-extension} \
    ${VIRTUAL-RUNTIME_webappmanager} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'webos-graphics-extended', ' \
        notificationmgr \
        packagegroup-webos-fonts \
    ', '', d)} \
"
