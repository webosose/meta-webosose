# Copyright (c) 2023-2024 LG Electronics, Inc.

DESCRIPTION = "Components for graphics added to webOS"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

PR = "r2"

inherit packagegroup
inherit features_check

ANY_OF_DISTRO_FEATURES = "webos-graphics webos-graphics-extended"

VIRTUAL-RUNTIME_surface-manager ?= "luna-surfacemanager-base"
VIRTUAL-RUNTIME_surface-manager-conf ?= "luna-surfacemanager-conf"
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
