# Copyright (c) 2023 LG Electronics, Inc.

inherit clang_cmake

require recipes-test/googletest/googletest_git.bb

PACKAGECONFIG += "${@bb.utils.contains('USE_WEBRUNTIME_LIBCXX', '1', 'webruntime-libcxx', 'system-libcxx', d)}"
PACKAGECONFIG[webruntime-libcxx] = ",,chromium-toolchain-native chromium-stdlib"
PACKAGECONFIG[system-libcxx] = ",,llvm-native clang"

PROVIDES = ""

PR = "r0"

do_install:append() {
    install -d ${D}/${LIBCBE_DIR}
    mv ${D}${libdir}/*.a ${D}/${LIBCBE_DIR}

    rm -rf ${D}${includedir}
    rm -rf ${D}${libdir}/cmake
    rm -rf ${D}${libdir}/pkgconfig
}

FILES:${PN}-staticdev += "${LIBCBE_DIR}/*.a"
