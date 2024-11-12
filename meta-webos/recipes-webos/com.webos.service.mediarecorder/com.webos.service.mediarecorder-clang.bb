# Copyright (c) 2024 LG Electronics, Inc.

inherit clang_cmake

require com.webos.service.mediarecorder.inc

WEBOS_REPO_NAME = "com.webos.service.mediarecorder"
FILESEXTRAPATHS:prepend := "${THISDIR}/com.webos.service.mediarecorder:"

PR = "${INC_PR}.0"

PACKAGECONFIG += "${@bb.utils.contains('USE_WEBRUNTIME_LIBCXX', '1', 'webruntime-libcxx', 'system-libcxx', d)}"
PACKAGECONFIG[webruntime-libcxx] = ",,chromium-toolchain-native chromium-stdlib"
PACKAGECONFIG[system-libcxx] = ",,libcxx"

PACKAGECONFIG += "build-buffer-encoder"

DEPENDS += "libpbnjson-clang"

EXTRA_OECMAKE += "-DWEBOS_CLANG_BUILD=ON"
