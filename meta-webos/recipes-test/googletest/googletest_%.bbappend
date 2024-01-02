# Copyright (c) 2015-2024 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos2"

FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"
SRC_URI += "file://0001-Revert-Googletest-export.patch"

do_configure:prepend() {
    sed -i 's@^#!/usr/bin/env python$@#!/usr/bin/env python3@g' ${S}/googletest/scripts/*py
}

# GTest developers recommend to use source code instead of linking
# against a prebuilt library.
do_install:append() {
    rm -rf ${B}/fused-src
    mkdir ${B}/fused-src
    ${S}/googletest/scripts/fuse_gtest_files.py ${B}/fused-src
    cp -vf ${S}/googletest/src/gtest_main.cc ${B}/fused-src/gtest/

    install -d ${D}${prefix}/src/gtest/src
    install -d ${D}${prefix}/src/gtest/cmake

    install -v -m 0644 ${B}/fused-src/gtest/* ${D}${prefix}/src/gtest/src
    # start with GOOGLETEST_VERSION from the root CMakeLists.txt
    grep '^set(GOOGLETEST_VERSION' ${S}/CMakeLists.txt > ${D}${prefix}/src/gtest/CMakeLists.txt
    cat  ${S}/googletest/CMakeLists.txt >> ${D}${prefix}/src/gtest/CMakeLists.txt
    install -v -m 0644 ${S}/googletest/cmake/* ${D}${prefix}/src/gtest/cmake
    install -v -m 0644 ${S}/googletest/cmake/gtest.pc.in ${D}${prefix}/src/gtest

    install -d ${D}${bindir}/gtest
    install -v -m 0755 ${S}/googletest/test/*.py ${D}${bindir}/gtest
}

SYSROOT_DIRS += "${prefix}/src"

FILES:${PN} += "${bindir}/gtest"
FILES:${PN}-dev += "${prefix}/src"
