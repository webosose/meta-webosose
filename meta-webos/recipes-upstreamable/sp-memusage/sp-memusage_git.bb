# Copyright (c) 2013-2025 LG Electronics, Inc.

SUMMARY = "A collection of memory usage monitoring tools and scripts"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=751419260aa954499f7abaabaa882bbe"

DEPENDS = "sp-measure"
RDEPENDS:${PN} = "sp-measure"

SRCREV = "65e6a729ce04cd90206689ae28fbabc4baf801f6"
PR = "r6"
PV = "1.3.2+git${SRCPV}"

inherit autotools-brokensep

SRC_URI = "git://github.com/maemo-tools-old/${BPN}.git;branch=master;protocol=https \
    file://0001-build-fixes.patch \
"
S = "${WORKDIR}/git"

FILES:${PN} += "${datadir}/sp-memusage-tests"

EXTRA_OEMAKE = "BINDIR=${bindir} LIBDIR=${libdir} DATADIR=${datadir}"

# | /jenkins/mjansa/build/webos/gatesgarth/BUILD/work/qemux86-webos-linux/sp-memusage/1.3.2+gitAUTOINC+65e6a729ce-r5/recipe-sysroot-native/usr/bin/i686-webos-linux/../../libexec/i686-webos-linux/gcc/i686-webos-linux/10.2.0/ld: error: /tmp/cclzkt11.o: multiple definition of 'sp_report_alignment_t'
# | /jenkins/mjansa/build/webos/gatesgarth/BUILD/work/qemux86-webos-linux/sp-memusage/1.3.2+gitAUTOINC+65e6a729ce-r5/recipe-sysroot-native/usr/bin/i686-webos-linux/../../libexec/i686-webos-linux/gcc/i686-webos-linux/10.2.0/ld: /tmp/ccYygIT3.o: previous definition here
CC += "-fcommon"
