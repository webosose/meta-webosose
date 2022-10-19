# Copyright (c) 2022 LG Electronics, Inc.

SUMMARY = "Solana program library"
HOMEPAGE = "https://github.com/solana-labs/solana-program-library"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://LICENSE;md5=f75ee68af8d19d48270eec815cc414fd \
"
inherit cargo
inherit pkgconfig

SRC_URI += "git://github.com/solana-labs/solana-program-library.git;protocol=https;branch=master"
# matches tag token-cli-v2.1.1
SRCREV = "88b147506d5b9515f3a4762421a0b8c309188dc9"
PV = "2.1.1"
require ${BPN}-crates.inc

S = "${WORKDIR}/git"
CARGO_SRC_DIR = "token/cli"
DEPENDS += "udev openssl clang-native"

export OPENSSL_NO_VENDOR = "1"
