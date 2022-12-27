# Copyright (c) 2012-2023 LG Electronics, Inc.

SUMMARY = "Components useful to developers added to webOS OSE -devel images"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

# You don't need to change this value when you're changing just RDEPENDS:${PN} variable.
PR = "r1"

PACKAGE_ARCH = "${MACHINE_ARCH}"
inherit packagegroup

VALGRIND = "valgrind"
# only armv7a is supported
VALGRIND:arm = ""
VALGRIND:armv7a = "valgrind"
VALGRIND:armv7ve = "valgrind"

# libpcap is a dependency library for iftop
RDEPENDS:${PN} = "\
    binutils \
    htop \
    iftop \
    libpcap \
    nmon \
    qml-webos-framework-tools \
    sp-memusage \
    ${VALGRIND} \
"
