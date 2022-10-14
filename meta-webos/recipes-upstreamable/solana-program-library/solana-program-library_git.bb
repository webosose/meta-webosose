# Copyright (c) 2022 LG Electronics, Inc.

SUMMARY = "Solana program library"
HOMEPAGE = "https://github.com/solana-labs/solana-program-library"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://${S}/../../LICENSE;md5=f75ee68af8d19d48270eec815cc414fd \
"
inherit webos_cargo

SRC_URI += "git://github.com/solana-labs/solana-program-library.git;protocol=https;branch=master"
SRCREV = "${AUTOREV}"

S = "${WORKDIR}/git/token/cli"
DEPENDS += "udev clang-native"
