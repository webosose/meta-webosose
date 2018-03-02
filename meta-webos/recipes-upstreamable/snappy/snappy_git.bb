# Copyright (c) 2015-2016 LG Electronics, Inc.

SUMMARY = "Snappy, a fast compressor/decompressor"
DESCRIPTION = "Snappy is a compression/decompression library that \
               aims for very high speeds and reasonable compression"
HOMEPAGE = "https://github.com/google/snappy"
SECTION = "libs"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://COPYING;md5=d4395b19c5d390309ac1c314d9966e9e"
PV = "1.1.2+git${SRCPV}"
PR = "r2"

SRCREV = "1ff9be9b8fafc8528ca9e055646f5932aa5db9c4"
SRC_URI = "git://github.com/google/${BPN}"

inherit autotools pkgconfig

EXTRA_OECONF = "--enable-shared --disable-static --disable-gtest"

S = "${WORKDIR}/git"

BBCLASSEXTEND = "native"
