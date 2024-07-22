# Copyright (c) 2022-2024 LG Electronics, Inc.

SUMMARY = "Solana program library"
HOMEPAGE = "https://github.com/solana-labs/solana-program-library"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://LICENSE;md5=f75ee68af8d19d48270eec815cc414fd \
"
inherit cargo
inherit pkgconfig

SRC_URI += "git://github.com/solana-labs/solana-program-library.git;protocol=https;branch=master"
# one commit after tag token-cli-v2.1.1
SRCREV = "8177a4d9e29c8df408ac9499edea5290bddf2311"
PV = "2.1.1"
require ${BPN}-crates.inc

S = "${WORKDIR}/git"
CARGO_SRC_DIR = "token/cli"
DEPENDS += "udev openssl clang-native"

export OPENSSL_NO_VENDOR = "1"

inherit cargo-update-recipe-crates

# Fails to build on 32bit archs:
# qemux86: http://gecko.lge.com:8000/Errors/Details/576615
# raspberrypi4: http://gecko.lge.com:8000/Errors/Details/576621
# raspberrypi3: http://gecko.lge.com:8000/Errors/Details/576693
# error[E0512]: cannot transmute between types of different sizes, or dependently-sized types
#    --> /usr/src/debug/solana-program-library/2.1.1-r0/cargo_home/bitbake/solana_rbpf-0.2.31/src/interpreter.rs:464:35
#     |
# 464 |                         (unsafe { std::mem::transmute::<u64, SyscallFunction::<E, *mut u8>>(syscall.function) })(
#     |                                   ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
#     |
#     = note: source type: `u64` (64 bits)
#     = note: target type: `for<'r, 's, 't0> fn(*mut u8, u64, u64, u64, u64, u64, &'r mut MemoryMapping<'s>, &'t0 mut std::result::Result<u64, EbpfError<E>>)` (32 bits)
COMPATIBLE_MACHINE = "(-)"
COMPATIBLE_MACHINE:aarch64 = "(.*)"
COMPATIBLE_MACHINE:x86-64 = "(.*)"

# FIXME-buildpaths!!!
# [WRP-10883] buildpath QA issues
# http://gecko.lge.com:8000/Errors/Details/894435
# ERROR: QA Issue: File /usr/bin/.debug/spl-token in package solana-program-library-dbg contains reference to TMPDIR [buildpaths]
ERROR_QA:remove = "buildpaths"
WARN_QA:append = " buildpaths"
