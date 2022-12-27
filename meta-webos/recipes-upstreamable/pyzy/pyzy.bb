# Copyright (c) 2019-2023 LG Electronics, Inc.

SUMMARY = "The Chinese PinYin and Bopomofo conversion library"
AUTHOR = "Pugalendhi Ganesan <pugalendhi.ganesan@lge.com>"
HOMEPAGE = "https://github.com/pyzy/pyzy"
SECTION = "libs"
LICENSE = "LGPL-2.1-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=4b54a1fd55a448865a0b32d41598759d"

DEPENDS = "sqlite3 sqlite3-native glib-2.0 util-linux"

inherit gettext autotools pkgconfig

SRCREV = "d7747466562cb8b4bc2934708e29b7643c7bedbc"

PV = "0.1.0+git${SRCPV}"
PR = "r1"

SRC_URI = "git://github.com/pyzy/pyzy.git;branch=master;protocol=https \
    file://0001-Fix-Narrowing-conversion-from-int-to-char-bug.patch \
    file://0002-Remove-Cursor-line-from-auxiliary-text.patch \
    file://0003-Add-pyzy-wrapper-for-webOS.patch \
    file://0004-Change-static-path-to-user-path.patch \
    file://0005-Change-edit_end_byte-position.patch \
    file://0006-.py-use-python3-explicitly-and-migrate-with-2to3.patch \
    file://0007-More-tweaks-of-data-db-android-create_db.py.patch \
    file://0008-Add-utf-16-encoding-when-opening-the-phrase-file.patch \
"
S = "${WORKDIR}/git"
