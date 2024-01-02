# Copyright (c) 2023-2024 LG Electronics, Inc.

DESCRIPTION = "Components for media added to webOS"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

PR = "r2"

inherit packagegroup
inherit features_check

REQUIRED_DISTRO_FEATURES = "webos-media"

VIRTUAL-RUNTIME_com.webos.service.mediacontroller ?= ""
VIRTUAL-RUNTIME_com.webos.service.mediacontroller:raspberrypi4 = "com.webos.service.mediacontroller"
VIRTUAL-RUNTIME_com.webos.service.mediacontroller:qemux86 = "com.webos.service.mediacontroller"
VIRTUAL-RUNTIME_com.webos.service.mediacontroller:qemux86-64 = "com.webos.service.mediacontroller"

VIRTUAL-RUNTIME_umediaserver ?= "umediaserver"
VIRTUAL-RUNTIME_umediaserver:armv4 = ""
VIRTUAL-RUNTIME_umediaserver:armv5 = ""

VIRTUAL-RUNTIME_mediarecorder ?= "com.webos.service.mediarecorder"

RDEPENDS:${PN} = " \
    gstreamer1.0 \
    gstreamer1.0-libav \
    gstreamer1.0-plugins-bad \
    gstreamer1.0-plugins-base \
    gstreamer1.0-plugins-good \
    gstreamer1.0-plugins-ugly \
    ${VIRTUAL-RUNTIME_umediaserver} \
"

# Add to webos distro only
RDEPENDS:${PN}:append:webos = " \
    com.webos.app.videoplayer \
    ${VIRTUAL-RUNTIME_com.webos.service.mediacontroller} \
    ${VIRTUAL-RUNTIME_mediarecorder} \
"

# Add to qemu target only
RDEPENDS:${PN}:append:qemuall = " \
    com.webos.service.mediaindexer \
"

# Add to qemu target only as recommendations
RRECOMMENDS:${PN}:append:qemuall = " \
    kernel-module-media \
"
