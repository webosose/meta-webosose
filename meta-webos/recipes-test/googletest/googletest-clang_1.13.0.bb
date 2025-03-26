# Copyright (c) 2023-2025 LG Electronics, Inc.

inherit clang_cmake

require recipes-test/googletest/googletest_${PV}.bb

# Needed for meta-oe/recipes-test/googletest/files/0001-work-around-GCC-6-11-ADL-bug.patch from
# https://git.openembedded.org/meta-openembedded/commit/?h=kirkstone&id=8fb1e24fcc5fdaaafbfa03852c7b8bc3e995fe62
FILESEXTRAPATHS:prepend = "${META_OE_LAYER}/recipes-test/googletest/files:"

PACKAGECONFIG += "${@bb.utils.contains('USE_WEBRUNTIME_LIBCXX', '1', 'webruntime-libcxx', 'system-libcxx', d)}"
PACKAGECONFIG[webruntime-libcxx] = ",,chromium-toolchain-native chromium-stdlib"
PACKAGECONFIG[system-libcxx] = ",,libcxx"

PROVIDES = ""

PR = "r2"

do_install:append() {
    install -d ${D}/${LIBCBE_DIR}
    mv ${D}${libdir}/*.a ${D}/${LIBCBE_DIR}

    rm -rf ${D}${includedir}
    rm -rf ${D}${libdir}/cmake
    rm -rf ${D}${libdir}/pkgconfig
}

FILES:${PN}-staticdev += "${LIBCBE_DIR}/*.a"
