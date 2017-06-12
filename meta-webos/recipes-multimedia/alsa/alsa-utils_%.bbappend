# Copyright (c) 2014-2018 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos2"

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

# this patch adds --disable-libsamplerate configure option
SRC_URI += "file://disable-libsamplerate.patch"

DEPENDS_remove = "libsamplerate0"

PACKAGECONFIG = ""
PACKAGECONFIG[libsamplerate] = "--enable-libsamplerate,--disable-libsamplerate,libsamplerate0"

# We have disabled topology support in our alsa-lib bbappend, so this package
# won't be built by alsa-utils and we need to remove runtime dependency on it
RDEPENDS_${PN}_remove = "alsa-utils-alsatplg"
