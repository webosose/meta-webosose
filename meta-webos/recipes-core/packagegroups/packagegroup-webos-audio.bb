# Copyright (c) 2023-2024 LG Electronics, Inc.

DESCRIPTION = "Components for audio added to webOS"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

PR = "r0"

inherit packagegroup
inherit features_check

REQUIRED_DISTRO_FEATURES = "webos-audio"

VIRTUAL-RUNTIME_tts ?= "com.webos.service.tts"

RDEPENDS:${PN} = " \
    audiod \
"

# Add to webos distro only
RDEPENDS:${PN}:append:webos = " \
    ${VIRTUAL-RUNTIME_tts} \
"

# Add to qemu target only
RDEPENDS:${PN}:append:qemuall = " \
    com.webos.service.audiofocusmanager \
    com.webos.service.audiooutput \
"

# Add to qemu target only as recommendations
RRECOMMENDS:${PN}:append:qemuall = " \
    kernel-module-snd-usb-audio \
"
