# Copyright (c) 2013-2018 LG Electronics, Inc.

SUMMARY = "Programs for creating and maintenance of GUID Partition Table"
SECTION = "console/utils"
LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://COPYING;md5=59530bdf33659b29e73d4adb9f9f6552"
DEPENDS = "util-linux ncurses popt icu"

PR = "r1"

SRC_URI = "${SOURCEFORGE_MIRROR}/project/${BPN}/${BPN}/${PV}/${BP}.tar.gz \
           file://gptfdisk-0.8.10-convenience-1.patch"

SRC_URI[md5sum] = "9cf4246c181c324bdbd553fe9b348373"
SRC_URI[sha256sum] = "73e64151203ae0c347c488358e71ca582bb7fb7f0d66df86b71c42050390eb9b"

EXTRA_OEMAKE += "POPT=yes -e MAKEFLAGS="

do_install () {
    oe_runmake install DESTDIR=${D}
}

PACKAGES =+ "${PN}-gdisk ${PN}-cgdisk ${PN}-fixparts ${PN}-sgdisk"
FILES_${PN}-gdisk += "${base_sbindir}/gdisk"
FILES_${PN}-cgdisk += "${base_sbindir}/cgdisk"
FILES_${PN}-sgdisk += "${base_sbindir}/sgdisk"
FILES_${PN}-fixparts += "${base_sbindir}/fixparts"
