# Copyright (c) 2023 LG Electronics, Inc.

inherit clang_cmake

require media-resource-calculator.bb

WEBOS_REPO_NAME = "media-resource-calculator"

PR = "r2"

PACKAGECONFIG += "${@bb.utils.contains('USE_WEBRUNTIME_LIBCXX', '1', 'webruntime-libcxx', 'system-libcxx', d)}"
PACKAGECONFIG[webruntime-libcxx] = ",,chromium-toolchain-native chromium-stdlib"
PACKAGECONFIG[system-libcxx] = ",,llvm-native clang"

PKGCONFIG_DIR = "${libdir}/pkgconfig"

do_install:append() {
    install -d ${D}/${LIBCBE_DIR}
    mv ${D}/${libdir}/*.so ${D}/${libdir}/*.so.* ${D}/${LIBCBE_DIR}

    mv -n ${D}${includedir}/media-resource-calculator ${D}${includedir}/${BPN}

    sed -i '/^libdir=.*\/lib$/ s/$/\/cbe/' ${D}/${PKGCONFIG_DIR}/media-resource-calculator.pc
    mv -n ${D}/${PKGCONFIG_DIR}/media-resource-calculator.pc ${D}/${PKGCONFIG_DIR}/${BPN}.pc
}

FILES:${PN} += "${LIBCBE_DIR}/lib*${SOLIBS}"
FILES:${PN}-dev += "${LIBCBE_DIR}/lib*${SOLIBSDEV}"
