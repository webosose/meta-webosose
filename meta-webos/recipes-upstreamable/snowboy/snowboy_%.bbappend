# Copyright (c) 2019-2022 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos1"

inherit cmake

DEPENDS += "lapack"

FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"
SRC_URI += "file://snowboy.pc.in;subdir=git"

# snowboy offers arm/aarch64/x86-64 only for C++ implementation
COMPATIBLE_MACHINE = "rpi|aarch64|x86-64|qemux86-64"

# match static lib for each arch
SNOWBOY_ARCH:armv6 = "rpi"
SNOWBOY_ARCH:armv7a = "rpi"
SNOWBOY_ARCH:armv7ve = "rpi"
SNOWBOY_ARCH:aarch64 = "aarch64-ubuntu1604"
SNOWBOY_ARCH:x86-64 = "ubuntu64"

FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"
SRC_URI:append = " file://CMakeLists.txt;subdir=git"

INSANE_SKIP:${PN} += "textrel"

# RPI 32-bit and x86-64's static library doesn't build with c++11. So we need to avoid using CXX11_ABI.
SNOWBOY_EXTRA_FLAGS ?= "-D_GLIBCXX_USE_CXX11_ABI=0"
SNOWBOY_EXTRA_FLAGS:aarch64 = "-std=c++11"

EXTRA_OECMAKE:append = " -DBUILD_SHARED_LIB=OFF -DINSTALL_KEYWORD_MODELS=ON -DSNOWBOY_ARCH='${SNOWBOY_ARCH}' -DSNOWBOY_EXTRA_FLAGS='${SNOWBOY_EXTRA_FLAGS}'"

FILES:${PN}-dev += "${libdir}/snowboy/*.so"
# if BUILD_SHARED_LIB is OFF, below is for fixing when populate_sdk
# Collected errors:
# * Solver encountered 1 problem(s):
# * Problem 1/1:
# *   - nothing provides snowboy = 1.3.0-r0webos1 needed by snowboy-dev-1.3.0-r0webos1.raspberrypi4_64
RDEPENDS:${PN}-dev = ""
# Otherwise, this line is unnecessary when BUILD_SHARED_LIB is ON

# Add models package if needs keyword activation models
PACKAGES =+ "${PN}-models"
FILES:${PN}-models = "${sysconfdir}/snowboy/models/*"
