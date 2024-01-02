# Copyright (c) 2023-2024 LG Electronics, Inc.

SUMMARY = "xtl: Basic tools (containers, algorithms) used by other quantstack packages"
DESCRIPTION = "xtl: Basic tools (containers, algorithms) used by other quantstack packages"
AUTHOR = "Ki-Joong Lee <kijoong.lee@lge.com>"
SECTION = "libs"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=c12cbcb0f50cce3b0c58db4e3db8c2da"

SRCREV = "e697c91e2a3ac571d120d2b093fb3b250d060a7d"

SRC_URI = " \
    git://github.com/xtensor-stack/xtl.git;branch=master;protocol=https \
"

S = "${WORKDIR}/git"
PR = "r0"

inherit cmake

DEPENDS = "nlohmann-json"

EXTRA_OECMAKE = " \
    -DBUILD_TESTS=OFF \
"

# xtl is a header only C++ library, so the main package will be empty.
RDEPENDS:${PN}-dev = ""

BBCLASSEXTEND = "native nativesdk"
