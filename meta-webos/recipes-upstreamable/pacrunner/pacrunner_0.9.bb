# Copyright (c) 2016-2023 LG Electronics, Inc.

SUMMARY = "Proxy daemon"
AUTHOR = "Sungmok Shin <sungmok.shin@lge.com>"
SECTION = "console/utils"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=12f884d2ae1ff87c09e5b7ccc2c4ca7e"

DEPENDS = "dbus glib-2.0"

PR = "r1"

SRC_URI = "${KERNELORG_MIRROR}/linux/network/connman/${BP}.tar.gz"

SRC_URI[md5sum] = "c42397a392f9e898f3c32243d1027676"
SRC_URI[sha256sum] = "32ba57943f449c0c3912e919c3aff25c679b8ee61b5aa24ebf9913d5de284aa5"

inherit autotools pkgconfig

FILES:${PN} = "${datadir}/dbus-1/system-services/* ${sbindir}/* ${sysconfdir} ${sysconfdir}"
