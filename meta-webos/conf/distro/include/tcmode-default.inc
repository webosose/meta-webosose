# Copyright (c) 2014-2023 LG Electronics, Inc.
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
