# Copyright (c) 2013-2022 LG Electronics, Inc.

SUMMARY = "Open Source Full Database Encryption for SQLite"
DESCRIPTION = "SQLCipher is an open source library that provides transparent, secure 256-bit AES encryption of SQLite database files."
SECTION = "webos/libs"
LICENSE = "BSD"
DEPENDS = "tcl-native openssl"
LIC_FILES_CHKSUM = "file://LICENSE;md5=7edde5c030f9654613438a18b9b56308"

PV = "3.4.2"

inherit autotools

SRCREV = "c6f709fca81c910ba133aaf6330c28e01ccfe5f8"
SRC_URI = "git://github.com/${BPN}/${BPN};branch=master;protocol=https"

EXTRA_OECONF = "--disable-tcl CFLAGS=-DSQLITE_HAS_CODEC"
EXTRA_OEMAKE='"LIBTOOL=${STAGING_BINDIR_CROSS}/${HOST_SYS}-libtool"'

S = "${WORKDIR}/git"

VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS:${PN}:append:class-target = " ${VIRTUAL-RUNTIME_bash}"
