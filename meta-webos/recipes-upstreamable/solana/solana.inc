# Copyright (c) 2022 LG Electronics, Inc.

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://${S}/../LICENSE;md5=8b09f8d2772d701fd89dac877636a116 \
"
inherit webos_cargo

SRC_URI += "git://github.com/solana-labs/solana.git;protocol=https;nobranch=1"
SRCREV = "05ffda9b16bfa9c784b22bbb0483f5a567373ae5"

DEPENDS += "udev clang-native"
