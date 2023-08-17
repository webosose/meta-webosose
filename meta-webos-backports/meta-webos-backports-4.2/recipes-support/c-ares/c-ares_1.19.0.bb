# Backport from
# https://git.openembedded.org/meta-openembedded/commit/meta-oe/recipes-support/c-ares/c-ares_1.19.0.bb?id=4e35ff869b27114059f49fa3e7adefa98bd0db6e

# Copyright (c) 2012-2014 LG Electronics, Inc.
SUMMARY = "c-ares is a C library that resolves names asynchronously."
HOMEPAGE = "http://daniel.haxx.se/projects/c-ares/"
SECTION = "libs"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=fb997454c8d62aa6a47f07a8cd48b006"

SRC_URI = "git://github.com/c-ares/c-ares.git;branch=main;protocol=https"
SRCREV = "fddf01938d3789e06cc1c3774e4cd0c7d2a89976"

UPSTREAM_CHECK_GITTAGREGEX = "cares-(?P<pver>\d+_(\d_?)+)"

S = "${WORKDIR}/git"

inherit cmake pkgconfig

PACKAGES =+ "${PN}-utils"

FILES:${PN}-utils = "${bindir}"

BBCLASSEXTEND = "native nativesdk"
