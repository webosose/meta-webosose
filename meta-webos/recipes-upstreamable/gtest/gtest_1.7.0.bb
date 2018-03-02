# Copyright (c) 2013-2017 LG Electronics, Inc.

DESCRIPTION = "Google C++ Testing Framework"
HOMEPAGE = "https://code.google.com/p/googletest"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=cbbd27594afd089daa160d3a16dd515a"

PR = "r5"

# matches release-1.7.0 tag, which doesn't exist on any branch
SRCREV = "c99458533a9b4c743ed51537e25989ea55944908"
SRC_URI = "\
    git://github.com/google/googletest.git;nobranch=1 \
    file://cmake-Add-install-command-for-libraries-and-headers.patch \
    file://CMakeLists-gtest.pc.in-Add-pkg-config-support-to-gte.patch \
    file://0001-explicit-bool-cast.patch \
"
S = "${WORKDIR}/git"

inherit lib_package
inherit cmake

WEBOS_NO_STATIC_LIBRARIES_WHITELIST = "libgtest.a libgtest_main.a"

# GTest developers recommend to use source code instead of linking
# against a prebuilt library.
do_install() {
    mkdir ${B}/fused-src || true
    ${S}/scripts/fuse_gtest_files.py ${B}/fused-src
    cp -vf ${S}/src/gtest_main.cc ${B}/fused-src/gtest/

    install -d ${D}${includedir}/gtest/internal
    install -d ${D}${prefix}/src/gtest/src
    install -d ${D}${prefix}/src/gtest/cmake

    install -v -m 0644 ${S}/include/gtest/*.h ${D}${includedir}/gtest
    install -v -m 0644 ${S}/include/gtest/internal/*.h ${D}${includedir}/gtest/internal
    install -v -m 0644 ${B}/fused-src/gtest/* ${D}${prefix}/src/gtest/src
    install -v -m 0644 ${S}/CMakeLists.txt ${D}${prefix}/src/gtest
    install -v -m 0644 ${S}/cmake/* ${D}${prefix}/src/gtest/cmake
    install -v -m 0644 ${S}/gtest.pc.in ${D}${prefix}/src/gtest

    install -d ${D}${libdir}/pkgconfig

    install -v -m 0644 ${B}/*.a ${D}${libdir}
    install -v -m 0644 ${B}/gtest.pc ${D}${libdir}/pkgconfig

    install -d ${D}${bindir}/gtest
    install -v -m 0755 ${S}/test/*.py ${D}${bindir}/gtest
}

sysroot_stage_all_append() {
    sysroot_stage_dir ${D}${prefix}/src ${SYSROOT_DESTDIR}${prefix}/src
}

FILES_${PN} += "${bindir}/gtest"
FILES_${PN}-dev += "${prefix}/src"

ALLOW_EMPTY_${PN} = "1"
ALLOW_EMPTY_${PN}-dbg = "1"
