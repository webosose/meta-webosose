# Copyright (c) 2012-2025 LG Electronics, Inc.

SUMMARY = "Components useful to developers added to webOS OSE -devel images"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

PR = "r2"

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
    ${@bb.utils.contains_any('DISTRO_FEATURES', 'vulkan opengl', 'qml-webos-framework-tools', '', d)} \
    sp-memusage \
    ${VALGRIND} \
"
