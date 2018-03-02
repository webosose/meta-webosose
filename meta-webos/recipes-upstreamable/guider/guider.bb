# Copyright (c) 2015-2017 LG Electronics, Inc.

SUMMARY = "performance profiler"
DESCRIPTION = "monitoring cpu/memory/disk usage per thread/function"
AUTHOR = "Peace Lee <peace.lee@lge.com>"
HOMEPAGE = "https://github.com/iipeace/guider"

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://LICENSE;md5=2c1c00f9d3ed9e24fa69b932b7e7aff2"

PV = "3.5.8+git${SRCPV}"
PR = "r3"
RDEPENDS_${PN} = "python-shell"

SRC_URI = "git://github.com/iipeace/guider.git"
SRCREV = "17011836bea5dc5656b9bc2d77891a9fa7ecccd2"
S = "${WORKDIR}/git"

EXTRA_OEMAKE = "CC='${CC}' LDFLAGS='-shared ${LDFLAGS}'"

GUIDER_OBJ = "guider.pyc"
GUIDER_LAUNCHER = "guider"

do_install() {
    install -d ${D}${sbindir}
    install -v -m 0755 ${S}/${GUIDER_LAUNCHER} ${D}${sbindir}/${GUIDER_LAUNCHER}

    install -d ${D}${datadir}/${BPN}
    install -v -m 0755 ${S}/${GUIDER_OBJ} ${D}${datadir}/${BPN}/${GUIDER_OBJ}

    oe_libinstall -so -C ${S}/ libguider ${D}${libdir}
}

PACKAGES += "libguider"

# libguider.so needed for file profiler
FILES_SOLIBSDEV = ""
FILES_libguider = "${libdir}/libguider.so*"
