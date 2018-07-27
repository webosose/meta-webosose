# Imported from http://git.yoctoproject.org/cgit/cgit.cgi/meta-ivi/tree/recipes-support-ivi/keyutils/keyutils_1.5.5.bb
# commit id: 5d6bb58ad1733d867b7c0e6864b29ef7d8fefd80
# and modified to support aarch64, there is 1.5.9 version now in meta-ivi

DESCRIPTION = "Linux Key Management Utilities"
SECTION = "base"
LICENSE = "GPL-2.0 & LGPL-2.1"
LIC_FILES_CHKSUM = "file://LICENCE.GPL;md5=5f6e72824f5da505c1f4a7197f004b45 \
                    file://LICENCE.LGPL;md5=7d1cacaa3ea752b72ea5e525df54a21f"

PR = "r5"

SRCREV = "5cd9d711f650e03685ba8bf2099b2df30cf6e71a"

SRC_URI = "git://git.kernel.org/pub/scm/linux/kernel/git/dhowells/keyutils.git;protocol=git \
          file://keyutils_fix_library_install.patch \
          "
SRC_URI_append_arm = " file://keyutils-arm-remove-m32-m64.patch"
SRC_URI_append_aarch64 = " file://keyutils-arm-remove-m32-m64.patch"
SRC_URI_append_x86 = " file://keyutils_fix_x86_cflags.patch"
SRC_URI_append_x86-64 = " file://keyutils_fix_x86-64_cflags.patch"

S = "${WORKDIR}/git"

inherit autotools-brokensep

INSTALL_FLAGS = "BINDIR=/usr/bin SBINDIR=/usr/sbin DESTDIR=${D}"

do_install() {
	oe_runmake ${INSTALL_FLAGS} install
}

# http://caprica.lgsvl.com:8080/Errors/Details/1092065
# fixed in:
# http://git.yoctoproject.org/cgit/cgit.cgi/meta-ivi/commit/meta-ivi/recipes-support-ivi/keyutils?id=31f05de9c4cd04b3ff9bb6b3030d24be3be837d4
#
# don't use fortify here because the Makefile doesn't respect the flags from OE, nor uses any optimalization
# aarch64-webos-linux-gcc  -fstack-protector-strong  -D_FORTIFY_SOURCE=2 -Wformat -Wformat-security -Werror=format-security --sysroot=TOPDIR/BUILD/work/raspberrypi3_64-webos-linux/keyutils/1.5.5-r5/recipe-sysroot -I. -DPKGBUILD="\"2018-07-27\"" -DPKGVERSION="\"keyutils-1.5.5\"" -DAPIVERSION="\"libkeyutils-1.4\"" -g -Wall -Werror -UNO_GLIBC_KEYERR -o keyutils.o -c keyutils.c
# keyutils/1.5.5-r5/recipe-sysroot/usr/include/features.h:381:4: error: #warning _FORTIFY_SOURCE requires compiling with optimization (-O) [-Werror=cpp]
#  warning _FORTIFY_SOURCE requires compiling with optimization (-O)
#    ^~~~~~~
lcl_maybe_fortify = ""
