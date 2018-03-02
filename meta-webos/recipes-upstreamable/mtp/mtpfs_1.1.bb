# Copyright (c) 2014 LG Electronics, Inc.

SUMMARY = "FUSE filesystem module to mount media player"
DESCRIPTION = "mtpfs allows you to mount smart phone or media player"
SECTION = "console/utils"
LICENSE = "GPL-3.0"
LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504"
DEPENDS = "libusb libid3tag glib-2.0 libmtp fuse"
PR = "r2"

SRC_URI = "http://www.adebenham.com/files/mtp/mtpfs-${PV}.tar.gz \
    file://subfolder_error.patch \
    file://change_returnvalue.patch \
    file://memory_leak.patch \
"

SRC_URI[md5sum] = "a299cadca336e6945b7275b44c6e8d27"
SRC_URI[sha256sum] = "1baf357de16995a5f0b5bc1b6833517a77456481d861cdba70f1ce1316ce4c1d"

inherit autotools binconfig pkgconfig

EXTRA_OECONF = "--disable-mad --enable-debug "
