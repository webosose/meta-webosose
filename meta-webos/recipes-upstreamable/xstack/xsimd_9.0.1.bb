# Copyright (c) 2023-2024 LG Electronics, Inc.

SUMMARY = "xsimd: C++ wrappers for SIMD intrinsics"
DESCRIPTION = "xsimd: C++ wrappers for SIMD intrinsics."
AUTHOR = "Ki-Joong Lee <kijoong.lee@lge.com>"
SECTION = "libs"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=fdd4d7dc8bdd9ae7181dd0bca68007a2"

SRCREV = "31298c883bda0f3dfcf44170f45be242deb78f1a"

SRC_URI = " \
    git://github.com/xtensor-stack/xsimd.git;branch=master;protocol=https \
"

S = "${WORKDIR}/git"
PR = "r0"

inherit cmake

DEPENDS = "xtl"

EXTRA_OECMAKE = " \
    -DENABLE_XTL_COMPLEX=ON  \
"

# xsimd is a header only C++ library, so the main package will be empty.
RDEPENDS:${PN}-dev = ""

BBCLASSEXTEND = "native nativesdk"
