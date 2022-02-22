# Copyright (c) 2019-2022 LG Electronics, Inc.

SUMMARY = "FUSE filesystem module to mount camera"
HOMEPAGE = "http://www.gphoto.org/proj/gphotofs/"
SECTION = "console/utils"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=94d55d512a9ba36caa9b7df079bae19f"
DEPENDS = "fuse glib-2.0 libgphoto2 glib-2.0-native"

PR = "r0"

SRC_URI = "${SOURCEFORGE_MIRROR}/gphoto/files/${BP}.tar.bz2"

SRC_URI[md5sum] = "bf88054e726e27b9c699ac4ed594cdf6"
SRC_URI[sha256sum] = "676ec4de69a81c193ffc31bdc7b587ac2a2cc3780b14f0e7c9c4c0a517b343cc"

inherit gettext autotools pkgconfig
