# Copyright (c) 2012-2015 LG Electronics, Inc.

SECTION = "libs"
DESCRIPTION = "Hunspell spell checker and morphological analyzer"
LICENSE = "LGPL-2.1 & MPL-1.1"

LIC_FILES_CHKSUM = "file://COPYING.LGPL;md5=d8045f3b8f929c1cb29a1e3fd737b499"
LIC_FILES_CHKSUM += "file://COPYING.MPL;md5=bfe1f75d606912a4111c90743d6c7325"

PR = "r2"

inherit autotools-brokensep gettext

SRC_URI = "http://downloads.sourceforge.net/hunspell/hunspell-${PV}.tar.gz"
S = "${WORKDIR}/hunspell-${PV}"

do_install() {
    #oenote installing hunspell
    install -d ${D}${libdir}
    oe_libinstall -C ${S}/src/hunspell/.libs -so libhunspell-1.3 ${D}${libdir}
    install -d ${D}${includedir}/hunspell
    install -m 644 ${S}/src/hunspell/*.h ${D}${includedir}/hunspell/
    install -m 644 ${S}/src/hunspell/*.hxx ${D}${includedir}/hunspell/
}

SRC_URI[md5sum] = "3121aaf3e13e5d88dfff13fb4a5f1ab8"
SRC_URI[sha256sum] = "b4edd4a4ee944cb9f485b35473e46b729ed768e9d24da8e78e4c4c6ca56addbd"

