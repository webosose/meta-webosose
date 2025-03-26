# Copyright (c) 2024-2025 LG Electronics, Inc.

require com.webos.service.mediarecorder.inc

WEBOS_REPO_NAME = "com.webos.service.mediarecorder"
FILESEXTRAPATHS:prepend := "${THISDIR}/com.webos.service.mediarecorder:"

PR = "${INC_PR}.0"

PACKAGECONFIG += "build-buffer-encoder"

DEPENDS += "libpbnjson"

FILES:${PN} += "${libdir}/lib*${SOLIBS}"
FILES:${PN}-dev += "${libdir}/lib*${SOLIBSDEV}"
