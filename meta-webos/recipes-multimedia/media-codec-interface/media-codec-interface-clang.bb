# Copyright (c) 2023-2025 LG Electronics, Inc.

inherit clang_cmake

require media-codec-interface.inc

WEBOS_REPO_NAME = "media-codec-interface"

PR = "${INC_PR}.11"

CXXFLAGS +=" \
    -I${STAGING_INCDIR}/media-resource-calculator-clang \
    -I${STAGING_INCDIR}/cbe \
    -I${STAGING_INCDIR}/gst-video-encoder \
"

PACKAGECONFIG += "${@bb.utils.contains('USE_WEBRUNTIME_LIBCXX', '1', 'webruntime-libcxx', 'system-libcxx', d)}"
PACKAGECONFIG[webruntime-libcxx] = ",,chromium-toolchain-native chromium-stdlib"
PACKAGECONFIG[system-libcxx] = ",,libcxx"
DEPENDS_TO_REMOVE_MCIL ?= ""
DEPENDS_TO_ADD_MCIL ?= ""
DEPENDS:remove = "${DEPENDS_TO_REMOVE_MCIL}"
DEPENDS += "${DEPENDS_TO_ADD_MCIL}"


PKGCONFIG_DIR = "${datadir}/pkgconfig"

do_configure:prepend() {
    ln -snf media-resource-calculator-clang.pc ${STAGING_LIBDIR}/pkgconfig/media-resource-calculator.pc
}

do_configure:prepend() {
    ln -snf gst-video-encoder-clang.pc ${STAGING_DATADIR}/pkgconfig/gst-video-encoder.pc
}

do_install:append() {
    install -d ${D}/${LIBCBE_DIR}
    mv ${D}/${libdir}/*.so* ${D}/${LIBCBE_DIR}
    sed -i '/^libdir=.*\/lib$/ s/$/\/cbe/; /^libdir=.*\/lib32$/ s/$/\/cbe/; /^libdir=.*\/lib64$/ s/$/\/cbe/;' ${D}/${PKGCONFIG_DIR}/media-codec-interface.pc
}

FILES:${PN} += "${LIBCBE_DIR}/lib*${SOLIBS}"
FILES:${PN}-dev += "${LIBCBE_DIR}/lib*${SOLIBSDEV}"
