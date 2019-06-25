# Copyright (c) 2015-2019 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos1"

# GTest developers recommend to use source code instead of linking
# against a prebuilt library.
do_install_append() {
    mkdir ${B}/fused-src || true
    ${S}/googletest/scripts/fuse_gtest_files.py ${B}/fused-src
    cp -vf ${S}/googletest/src/gtest_main.cc ${B}/fused-src/gtest/

    install -d ${D}${prefix}/src/gtest/src
    install -d ${D}${prefix}/src/gtest/cmake

    install -v -m 0644 ${B}/fused-src/gtest/* ${D}${prefix}/src/gtest/src
    install -v -m 0644 ${S}/googletest/CMakeLists.txt ${D}${prefix}/src/gtest
    install -v -m 0644 ${S}/googletest/cmake/* ${D}${prefix}/src/gtest/cmake
    install -v -m 0644 ${S}/googletest/gtest.pc.in ${D}${prefix}/src/gtest
    
    install -d ${D}${bindir}/gtest
    install -v -m 0755 ${S}/googletest/test/*.py ${D}${bindir}/gtest
}

sysroot_stage_all_append() {
    sysroot_stage_dir ${D}${prefix}/src ${SYSROOT_DESTDIR}${prefix}/src
}

FILES_${PN} += "${bindir}/gtest"
FILES_${PN}-dev += "${prefix}/src"
