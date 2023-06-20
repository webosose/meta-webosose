# Copyright (c) 2016-2023 LG Electronics, Inc.

SUMMARY = "Proxy daemon"
AUTHOR = "Sungmok Shin <sungmok.shin@lge.com>"
SECTION = "console/utils"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=12f884d2ae1ff87c09e5b7ccc2c4ca7e"

DEPENDS = "dbus glib-2.0"

PR = "r2"

SRC_URI = "${KERNELORG_MIRROR}/linux/network/connman/${BP}.tar.gz \
    file://pacrunner.service \
"

SRC_URI[md5sum] = "c42397a392f9e898f3c32243d1027676"
SRC_URI[sha256sum] = "32ba57943f449c0c3912e919c3aff25c679b8ee61b5aa24ebf9913d5de284aa5"

inherit autotools pkgconfig

do_install:append() {
    install -d ${D}${sysconfdir}/init
#    install -v -m 644 ${WORKDIR}/pacrunner.conf ${D}${sysconfdir}/init

    install -d ${D}${sysconfdir}/systemd/system/
    install -v -m 0644 ${WORKDIR}/pacrunner.service ${D}${sysconfdir}/systemd/system/
}

FILES:${PN} = "${datadir}/dbus-1/system-services/* ${sbindir}/* ${sysconfdir} ${sysconfdir}"
