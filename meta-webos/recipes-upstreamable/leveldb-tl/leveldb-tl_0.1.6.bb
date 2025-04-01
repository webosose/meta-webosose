# Copyright (c) 2014-2025 LG Electronics, Inc.

SUMMARY = "LevelDB Template Library"
DESCRIPTION = "Template library to build a more complex storage schema with leveldb as a backend"
AUTHOR = "Nikolay Orliuk <virkony@gmail.com>"
HOMEPAGE = "https://github.com/ony/leveldb-tl"
SECTION = "libs"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=3b83ef96387f14655fc854ddc3c6bd57"
DEPENDS = "leveldb"
DEPENDS:append:class-target = " gtest"
PR = "r4"

SRC_URI = "git://github.com/ony/${BPN};branch=gcc-4.7;protocol=https \
    file://0001-util-Fix-build-with-gcc7.patch \
    file://0001-test_corners-initialize-cookie.patch \
    file://0001-Fix-build-with-gcc-13.patch \
    file://0001-CMakeLists.txt-replace-std-c-0x-with-std-c-14.patch \
"

# Use gcc-4.7/v0.1.6 tag. It's backport of gcc-4.8 branch,
# so it would work fine with both compiler versions
SRCREV = "b4f56ad18162c2e2f9acef93f0f42eac0f9f3163"

S = "${WORKDIR}/git"

inherit cmake

EXTRA_OECMAKE:append:class-native = " -DBUILD_TESTING:BOOL=false -DBUILD_MKSANDWICH:BOOL=false"

BBCLASSEXTEND = "native"
