# Copyright (c) 2022-2024 LG Electronics, Inc.

SUMMARY = "Sugar: Candy Machine CLI"
HOMEPAGE = "https://github.com/metaplex-foundation/sugar"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://LICENSE;md5=8336fc2fba757b150dcb81084783555e \
"
inherit cargo
inherit pkgconfig

SRC_URI = "git://github.com/metaplex-foundation/sugar.git;protocol=https;branch=main"
SRCREV = "a76d8e90225ed6bc05725667021203dd93e1a777"
require ${BPN}-crates.inc

S = "${WORKDIR}/git"
DEPENDS += "udev clang-native openssl"

inherit cargo-update-recipe-crates

# FIXME-buildpaths!!!
# [WRP-10883] buildpath QA issues
# http://gecko.lge.com:8000/Errors/Details/894437
# ERROR: QA Issue: File /usr/bin/.debug/sugar in package sugar-dbg contains reference to TMPDIR [buildpaths]
ERROR_QA:remove = "buildpaths"
WARN_QA:append = " buildpaths"
