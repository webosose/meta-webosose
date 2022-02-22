# Copyright (c) 2013-2022 LG Electronics, Inc.

SUMMARY = "A library for measuring resource consumption (CPU, memory)"
LICENSE = "LGPL-2.1-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=2d5025d4aa3495befef8f17206a5b0a1"

SRCREV = "bb4ced89842f8d2dc0b8a6bc5d631bb36355207b"
PR = "r2"
PV = "1.3.5+git${SRCPV}"

inherit autotools-brokensep

SRC_URI = "git://github.com/maemo-tools-old/${BPN}.git;branch=master;protocol=https \
    file://build-fixes-for-compiling-in-OE.patch \
    file://do-not-compile-doc-directory-if-doxygen-is-missing.patch \
"

S = "${WORKDIR}/git"
