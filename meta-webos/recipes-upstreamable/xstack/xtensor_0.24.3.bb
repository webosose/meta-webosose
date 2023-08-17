# Copyright (c) 2023 LG Electronics, Inc.

SUMMARY = "xtensor: Multi-dimensional arrays with broadcasting and lazy computing."
DESCRIPTION = "xtensor: Multi-dimensional arrays with broadcasting and lazy computing."
AUTHOR = "Ki-Joong Lee <kijoong.lee@lge.com>"
SECTION = "libs"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=5c67ec4d3eb9c5b7eed4c37e69571b93"

SRCREV = "9e8662ddad7fcd1a96969b96bf8955e59cf44978"

SRC_URI = " \
    git://github.com/xtensor-stack/xtensor.git;branch=master;protocol=https \
"

S = "${WORKDIR}/git"
PR = "r0"

inherit cmake

DEPENDS = "xtl nlohmann-json"

PACKAGECONFIG ?= "xsimd"

PACKAGECONFIG[xsimd] = " \
    -DXTENSOR_USE_XSIMD:BOOL=TRUE, \
    -DXTENSOR_USE_XSIMD:BOOL=FALSE, \
    xsimd \
"

PACKAGECONFIG[openmp] = " \
    -DXTENSOR_USE_OPENMP:BOOL=TRUE, \
    -DXTENSOR_USE_OPENMP:BOOL=FALSE, \
    openmp \
"

EXTRA_OECMAKE = " \
"

# xtensor is a header only C++ library, so the main package will be empty.
RDEPENDS:${PN}-dev = ""

BBCLASSEXTEND = "native nativesdk"
