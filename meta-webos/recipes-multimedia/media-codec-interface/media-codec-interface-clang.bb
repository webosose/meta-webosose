# Copyright (c) 2023-2024 LG Electronics, Inc.

inherit clang_cmake

require media-codec-interface.bb

WEBOS_REPO_NAME = "media-codec-interface"

PR = "r5"

CXXFLAGS +=" \
    -I${STAGING_INCDIR}/media-resource-calculator-clang \
    -I${STAGING_INCDIR}/cbe \
"

PACKAGECONFIG += "${@bb.utils.contains('USE_WEBRUNTIME_LIBCXX', '1', 'webruntime-libcxx', 'system-libcxx', d)}"
PACKAGECONFIG[webruntime-libcxx] = ",,chromium-toolchain-native chromium-stdlib"
PACKAGECONFIG[system-libcxx] = ",,llvm-native clang"
DEPENDS:remove = "media-resource-calculator umediaserver"
DEPENDS += "media-resource-calculator-clang umediaserver-clang"

PKGCONFIG_DIR = "${datadir}/pkgconfig"

do_configure:prepend() {
    ln -snf media-resource-calculator-clang.pc ${STAGING_LIBDIR}/pkgconfig/media-resource-calculator.pc
}

do_install:append() {
    install -d ${D}/${LIBCBE_DIR}
    mv ${D}/${libdir}/*.so* ${D}/${LIBCBE_DIR}
    sed -i '/^libdir=.*\/lib$/ s/$/\/cbe/' ${D}/${PKGCONFIG_DIR}/media-codec-interface.pc
}

FILES:${PN} += "${LIBCBE_DIR}/lib*${SOLIBS}"
FILES:${PN}-dev += "${LIBCBE_DIR}/lib*${SOLIBSDEV}"
