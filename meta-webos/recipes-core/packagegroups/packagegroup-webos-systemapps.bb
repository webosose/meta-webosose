# Copyright (c) 2023-2025 LG Electronics, Inc.

DESCRIPTION = "Components for system apps added to webOS"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

PR = "r1"

inherit packagegroup

VIRTUAL-RUNTIME_com.webos.app.home ?= "com.webos.app.home"
VIRTUAL-RUNTIME_com.webos.app.home:armv4 = ""
VIRTUAL-RUNTIME_com.webos.app.home:armv5 = ""

VIRTUAL-RUNTIME_settingsapp ?= "com.webos.app.settings"
VIRTUAL-RUNTIME_settingsapp:armv4 = ""
VIRTUAL-RUNTIME_settingsapp:armv5 = ""

RDEPENDS:${PN} = " \
    ${VIRTUAL-RUNTIME_com.webos.app.home} \
    ${VIRTUAL-RUNTIME_settingsapp} \
"
