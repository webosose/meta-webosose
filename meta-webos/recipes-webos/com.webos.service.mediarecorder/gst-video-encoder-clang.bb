# Copyright (c) 2024-2025 LG Electronics, Inc.

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

PKGCONFIG_DIR = "${datadir}/pkgconfig"

do_install:append() {
    install -d ${D}/${LIBCBE_DIR}
    mv ${D}/${libdir}/*.so* ${D}/${LIBCBE_DIR}
    sed -i '/^libdir=.*\/lib$/ s/$/\/cbe/; /^libdir=.*\/lib32$/ s/$/\/cbe/; /^libdir=.*\/lib64$/ s/$/\/cbe/;' ${D}/${PKGCONFIG_DIR}/gst-video-encoder.pc
    mv -n ${D}/${PKGCONFIG_DIR}/gst-video-encoder.pc ${D}/${PKGCONFIG_DIR}/${BPN}.pc
}

FILES:${PN} += "${LIBCBE_DIR}/lib*${SOLIBS}"
FILES:${PN}-dev += "${LIBCBE_DIR}/lib*${SOLIBSDEV}"
