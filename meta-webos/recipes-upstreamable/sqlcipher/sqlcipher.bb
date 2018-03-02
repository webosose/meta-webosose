# Copyright (c) 2013-2017 LG Electronics, Inc.

SUMMARY = "Open Source Full Database Encryption for SQLite"
DESCRIPTION = "SQLCipher is an open source library that provides transparent, secure 256-bit AES encryption of SQLite database files."
SECTION = "webos/libs"
LICENSE = "BSD"
DEPENDS = "tcl-native openssl"
LIC_FILES_CHKSUM = "file://LICENSE;md5=7edde5c030f9654613438a18b9b56308"

PR = "r3"
PV = "3.0.0"

inherit autotools

SRCREV = "c7986f290533bfe5b6984cad20cfec40571ce0da"
SRC_URI = "git://github.com/${BPN}/${BPN}"

EXTRA_OECONF = "--disable-tcl CFLAGS=-DSQLITE_HAS_CODEC"
EXTRA_OEMAKE='"LIBTOOL=${STAGING_BINDIR_CROSS}/${HOST_SYS}-libtool"'

S = "${WORKDIR}/git"

VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS_${PN}_append_class-target = " ${VIRTUAL-RUNTIME_bash}"
