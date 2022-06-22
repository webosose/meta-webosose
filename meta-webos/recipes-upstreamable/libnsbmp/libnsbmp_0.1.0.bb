# Copyright (c) 2013-2022 LG Electronics, Inc.

SUMMARY = "BMP Library"
DESCRIPTION = "Libnsbmp is a decoding library for BMP and ICO image file formats, written in C"
HOMEPAGE = "http://www.netsurf-browser.org/projects/libnsbmp/"
SECTION = "libs"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://COPYING;beginline=7;md5=f2b61f6f29dddc6cffb3a86382ff97b1"

PR = "r1"

inherit autotools pkgconfig cmake

SRC_URI = "http://download.netsurf-browser.org/libs/releases/${BP}-src.tar.gz \
    file://replace-makefile-with-cmake.patch \
"
SRC_URI[md5sum] = "5b33ff44dfb48e628bcadbe7e51edf90"
SRC_URI[sha256sum] = "fb576af6bd4d02d3626d5c2092bc06c80b2a80089a14decf40c813d9ec80ddc0"
