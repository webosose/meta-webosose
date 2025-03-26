# Copyright (c) 2014-2025 LG Electronics, Inc.
#
# Things that must be added when using the internal OE toolchain, i.e., when
# TCMODE is "default".
#

require ${COREBASE}/meta/conf/distro/include/tcmode-default.inc

# And this is needed for libgcc and gcc-runtime (which are TUNE_PKGARCH) to be
# built with the same signatures when they depend on by default MACHINE_ARCH
# glibc
PACKAGE_ARCH:pn-glibc = "${TUNE_PKGARCH}"
PACKAGE_ARCH:pn-glibc-initial = "${TUNE_PKGARCH}"
PACKAGE_ARCH:pn-glibc-locale = "${TUNE_PKGARCH}"
PACKAGE_ARCH:pn-glibc-mtrace = "${TUNE_PKGARCH}"
PACKAGE_ARCH:pn-glibc-scripts = "${TUNE_PKGARCH}"
PACKAGE_ARCH:pn-linux-libc-headers = "${TUNE_PKGARCH}"
PACKAGE_ARCH:pn-libgcc-initial = "${TUNE_PKGARCH}"

LIBC_DEPENDENCIES:remove = "libsegfault"

# https://lists.openembedded.org/g/openembedded-core/message/179469
# Allow to independently set P_V for llvm* recipes and actual llvm version used in other places
# This is needed for meta-clang which provides different llvm version from clang recipes, see:
# https://github.com/kraj/meta-clang/pull/766
LLVM_PREFERRED_VERSION ?= "14.0.6"
LLVMVERSION ?= "${LLVM_PREFERRED_VERSION}"

PREFERRED_VERSION_llvm = "${LLVM_PREFERRED_VERSION}"
PREFERRED_VERSION_llvm-native = "${LLVM_PREFERRED_VERSION}"
PREFERRED_VERSION_nativesdk-llvm = "${LLVM_PREFERRED_VERSION}"
