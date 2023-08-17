# Copyright (c) 2023 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos1"

# Sent to meta-oe in:
# https://lists.openembedded.org/g/openembedded-devel/message/103941
FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"
SRC_URI += "file://CVE-2019-17362.patch"

# Sent to meta-oe in:
# https://lists.openembedded.org/g/openembedded-devel/message/103942
# https://lists.openembedded.org/g/openembedded-devel/message/103943
PACKAGECONFIG ??= "ltm"
PACKAGECONFIG[ltm] = ",,libtommath"

CFLAGS += "${@bb.utils.contains('PACKAGECONFIG', 'ltm', '-DUSE_LTM -DLTM_DESC', '', d)}"

inherit pkgconfig

EXTRA_OEMAKE += "'PREFIX=${prefix}' 'DESTDIR=${D}' 'LIBPATH=${libdir}' 'CFLAGS=${CFLAGS}'"
