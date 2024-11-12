# Copyright (c) 2024 LG Electronics, Inc.

inherit clang_cmake

require com.webos.service.mediarecorder.inc

WEBOS_REPO_NAME = "com.webos.service.mediarecorder"
FILESEXTRAPATHS:prepend := "${THISDIR}/com.webos.service.mediarecorder:"

PR = "${INC_PR}.1"

PACKAGECONFIG += "${@bb.utils.contains('USE_WEBRUNTIME_LIBCXX', '1', 'webruntime-libcxx', 'system-libcxx', d)}"
PACKAGECONFIG[webruntime-libcxx] = ",,chromium-toolchain-native chromium-stdlib"
PACKAGECONFIG[system-libcxx] = ",,libcxx"

PACKAGECONFIG += "build-buffer-encoder"

DEPENDS += "libpbnjson-clang"

EXTRA_OECMAKE += "-DWEBOS_CLANG_BUILD=ON"

PKGCONFIG_DIR = "${datadir}/pkgconfig"

do_install() {
    install -d ${D}/${LIBCBE_DIR}
    cp -R --no-dereference --preserve=mode,links -v ${B}/bufferencoder/libbuffer-encoder.so* ${D}/${LIBCBE_DIR}

    install -d ${D}/${PKGCONFIG_DIR}
    install -v -m 644 ${B}/buffer-encoder.pc ${D}/${PKGCONFIG_DIR}/buffer-encoder.pc
    sed -i '/^libdir=.*\/lib$/ s/$/\/cbe/; /^libdir=.*\/lib32$/ s/$/\/cbe/; /^libdir=.*\/lib64$/ s/$/\/cbe/;' ${D}/${PKGCONFIG_DIR}/buffer-encoder.pc

    install -d ${D}${includedir}/mrp
    install -v -m 644 \
        ${S}/bufferencoder/*.h \
        ${S}/pipeline/src/base/base.h \
        ${S}/pipeline/src/base/message.h \
        ${S}/pipeline/src/log/glog.h \
        ${D}/${includedir}/mrp
}

FILES:${PN} += "${LIBCBE_DIR}/lib*${SOLIBS}"
FILES:${PN}-dev += "${LIBCBE_DIR}/lib*${SOLIBSDEV}"
