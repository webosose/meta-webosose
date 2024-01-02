# Copyright (c) 2022-2024 LG Electronics, Inc.

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://LICENSE;md5=8b09f8d2772d701fd89dac877636a116 \
"
inherit cargo
inherit pkgconfig

SRC_URI += "git://github.com/solana-labs/solana.git;protocol=https;nobranch=1"
SRCREV = "05ffda9b16bfa9c784b22bbb0483f5a567373ae5"
S = "${WORKDIR}/git"
require solana-crates.inc

DEPENDS += "udev clang-native"

inherit cargo-update-recipe-crates

# Fails to build on 32bit archs:
# qemux86: http://gecko.lge.com:8000/Errors/Details/576616
# raspberrypi4: http://gecko.lge.com:8000/Errors/Details/576622
# raspberrypi3: http://gecko.lge.com:8000/Errors/Details/576694
# error[E0512]: cannot transmute between types of different sizes, or dependently-sized types
#    --> /usr/src/debug/solana-cli/1.14.5-r0/cargo_home/bitbake/solana_rbpf-0.2.31/src/vm.rs:615:26
#     |
# 615 |                 unsafe { std::mem::transmute(syscall.init) };
#     |                          ^^^^^^^^^^^^^^^^^^^
#     |
#     = note: source type: `u64` (64 bits)
#     = note: target type: `fn(C) -> Box<dyn SyscallObject<E>>` (32 bits)
# error[E0512]: cannot transmute between types of different sizes, or dependently-sized types
#    --> /usr/src/debug/solana-cli/1.14.5-r0/cargo_home/bitbake/solana_rbpf-0.2.31/src/interpreter.rs:464:35
#     |
# 464 |                         (unsafe { std::mem::transmute::<u64, SyscallFunction::<E, *mut u8>>(syscall.function) })(
#     |                                   ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
#     |
#     = note: source type: `u64` (64 bits)
#     = note: target type: `for<'r, 's, 't0> fn(*mut u8, u64, u64, u64, u64, u64, &'r mut MemoryMapping<'s>, &'t0 mut std::result::Result<u64, EbpfError<E>>)` (32 bits)
# For more information about this error, try `rustc --explain E0512`.
# error: could not compile `solana_rbpf` due to 2 previous errors
COMPATIBLE_MACHINE = "(-)"
COMPATIBLE_MACHINE:aarch64 = "(.*)"
COMPATIBLE_MACHINE:x86-64 = "(.*)"
