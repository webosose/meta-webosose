# Copyright (c) 2023-2025 LG Electronics, Inc.

DESCRIPTION = "Components for fonts added to webOS"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

PR = "r2"

inherit packagegroup

VIRTUAL-RUNTIME_browser_fonts ?= "webos-fonts"

RDEPENDS:${PN} = " \
    fontconfig-utils \
    ${VIRTUAL-RUNTIME_browser_fonts} \
    webos-fontconfig-files \
"
