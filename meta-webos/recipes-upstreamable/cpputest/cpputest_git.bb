# Copyright (c) 2013-2017 LG Electronics, Inc.

SUMMARY = "CppUTest is a C/C++ based unit xUnit test framework for unit testing."
HOMEPAGE = "http://cpputest.github.io/"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://COPYING;md5=ce5d5f1fe02bcd1343ced64a06fd4177"

SRCREV = "ee14dc0646250d601a1d7f4f8f06ec142e65917b"
PV = "3.5+git${SRCPV}"
PR = "r4"

inherit cmake

SRC_URI = "git://github.com/${BPN}/${BPN} \
           file://cpputest.pc \
           file://0001-Modification-between-cpputest-v3.5-and-specific-func.patch"

S = "${WORKDIR}/git"

do_install_append() {
    install -d ${D}${libdir}/pkgconfig
    install -v -m 644 ${WORKDIR}/${BPN}.pc ${D}${libdir}/pkgconfig
}

# MemoryReportFormatter.cpp:52:143: error: format '%lu' expects argument of type 'long unsigned int', but argument 3 has type 'size_t {aka unsigned int}' [-Werror=format=]
CXXFLAGS += "-Wno-error=format"

SRC_URI += "file://0001-Search-replace-this-0-to-false-and-0-to-true.patch \
    file://0002-Fix-build-with-gcc-6.patch \
"

