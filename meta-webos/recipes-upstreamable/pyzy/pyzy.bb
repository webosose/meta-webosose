# Copyright (c) 2019 LG Electronics, Inc.

SUMMARY = "The Chinese PinYin and Bopomofo conversion library"
AUTHOR = "Pugalendhi Ganesan <pugalendhi.ganesan@lge.com>"
HOMEPAGE = "https://github.com/pyzy/pyzy"
SECTION = "libs"
LICENSE = "LGPLv2.1+"
LIC_FILES_CHKSUM = "file://COPYING;md5=4b54a1fd55a448865a0b32d41598759d"

DEPENDS = "sqlite3 sqlite3-native glib-2.0 util-linux"

inherit gettext autotools pkgconfig

SRCREV = "d7747466562cb8b4bc2934708e29b7643c7bedbc"

PV = "0.1.0+git${SRCPV}"
PR = "r0"

SRC_URI = "git://github.com/pyzy/pyzy.git \
    file://0001-Fix-Narrowing-conversion-from-int-to-char-bug.patch \
    file://0002-Remove-Cursor-line-from-auxiliary-text.patch \
    file://0003-Add-pyzy-wrapper-for-webOS.patch \
    file://0004-Change-static-path-to-user-path.patch \
    file://0005-Change-edit_end_byte-position.patch \
"
S = "${WORKDIR}/git"


