#
# Copyright (c) 2014-2017 LG Electronics, Inc.
#
# Things that must be added when using the internal OE toolchain, i.e., when
# TCMODE is "default".
#

require ${COREBASE}/meta/conf/distro/include/tcmode-default.inc

# We didn't need this when only qemux86 was using the internal OE toolchain
# because external-lg-toolchain and external-lg-toolchain-eglibc-locale set
# WEBOS_PREBUILT_BINARIES_FOR = "arm", which does the equivalent of blacklist-ing
# them for non-arm MACHINE-s.
PNBLACKLIST[external-lg-toolchain] ?= "internal OE toolchain is preferred"
PNBLACKLIST[external-lg-toolchain-eglibc-locale] ?= "internal OE toolchain is preferred"
PNBLACKLIST[nativesdk-external-lg-toolchain-eglibc-locale] ?= "internal OE toolchain is preferred"

# And this is needed for libgcc and gcc-runtime (which are TUNE_PKGARCH) to be
# built with the same signatures when they depend on by default MACHINE_ARCH
# glibc
PACKAGE_ARCH_pn-glibc = "${TUNE_PKGARCH}"
PACKAGE_ARCH_pn-glibc-initial = "${TUNE_PKGARCH}"
PACKAGE_ARCH_pn-glibc-locale = "${TUNE_PKGARCH}"
PACKAGE_ARCH_pn-glibc-mtrace = "${TUNE_PKGARCH}"
PACKAGE_ARCH_pn-glibc-scripts = "${TUNE_PKGARCH}"
PACKAGE_ARCH_pn-linux-libc-headers = "${TUNE_PKGARCH}"
PACKAGE_ARCH_pn-libgcc-initial = "${TUNE_PKGARCH}"
