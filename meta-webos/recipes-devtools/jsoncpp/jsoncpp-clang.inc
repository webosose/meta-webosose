# Copyright (c) 2023 LG Electronics, Inc.

inherit clang_cmake

PR = "r2"

PACKAGECONFIG += "${@bb.utils.contains('USE_WEBRUNTIME_LIBCXX', '1', 'webruntime-libcxx', 'system-libcxx', d)}"
PACKAGECONFIG[webruntime-libcxx] = ",,chromium-toolchain-native chromium-stdlib"
PACKAGECONFIG[system-libcxx] = ",,llvm-native clang"

OECMAKE_CXX_FLAGS += "-DJSON_USE_EXCEPTION=0"

PKGCONFIG_DIR = "${libdir}/pkgconfig"

do_install:append() {
    install -d ${D}/${LIBCBE_DIR}
    mv ${D}/${libdir}/*.so ${D}/${libdir}/*.so.* ${D}/${LIBCBE_DIR}

    install -d ${D}${includedir}/cbe
    mv -n ${D}${includedir}/json ${D}${includedir}/cbe

    sed -i '/^libdir=.*\/lib$/ s/$/\/cbe/' ${D}/${PKGCONFIG_DIR}/jsoncpp.pc
    mv -n ${D}/${PKGCONFIG_DIR}/jsoncpp.pc ${D}/${PKGCONFIG_DIR}/${BPN}.pc

    rm -rf ${D}${libdir}/cmake
}

FILES:${PN} += "${LIBCBE_DIR}/lib*${SOLIBS}"
FILES:${PN}-dev += "${LIBCBE_DIR}/lib*${SOLIBSDEV}"
