# Copyright (c) 2013-2022 LG Electronics, Inc.

DESCRIPTION = "C++ utility and widget library based on glibmm and gtkmm"
HOMEPAGE = "http://code.google.com/p/gtkmm-utils/"
SECTION = "libs"

LICENSE = "LGPL-2.1-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=9740aaebee0708fe3dd67fdc3f6902b2"

DEPENDS = "glibmm"

PR = "r2"

# matches 0.4.1 tag
SRCREV = "997e27bdef8e2bd4504fad1afb7cfd0b5b4eef68"

SRC_URI = " \
    git://github.com/markoa/${BPN}.git;branch=master;protocol=https \
    file://0001-configure-add-option-to-disable-building-gtkmm-utils.patch \
    file://0002-Fix-includes-with-newer-glib.patch \
    file://0003-Fix-log-definition.patch \
"
S = "${WORKDIR}/git"

PACKAGECONFIG ??= "${@bb.utils.contains('DISTRO_FEATURES', 'x11', 'gtkmm', '', d)}"
PACKAGECONFIG[gtkmm] = "--enable-gtkmm,--disable-gtkmm,gtkmm"

inherit autotools pkgconfig
