# Copyright (c) 2013-2020 LG Electronics, Inc.

AUTHOR = "Bhooshan Supe <bhooshan.supe@lge.com>"
EXTENDPRAUTO_append = "webos1"

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"
SRC_URI += "file://0001-Add-ignore-mtime-option-to-fc-cache.patch"
# Update the beginline and endline parameters, because the patch
# adds 4 new lines before it
# file://src/fccache.c;beginline=1367;endline=1382;md5=0326cfeb4a7333dd4dd25fbbc4b9f27f"
LIC_FILES_CHKSUM = "file://COPYING;md5=7a0449e9bc5370402a94c00204beca3d \
    file://src/fcfreetype.c;endline=45;md5=5d9513e3196a1fbfdfa94051c09dfc84 \
    file://src/fccache.c;beginline=1371;endline=1386;md5=0326cfeb4a7333dd4dd25fbbc4b9f27f \
"

do_install_append() {
    # remove the links to the config files from the fontconfig
    # component that conflict with the webOS config files
    rm -f ${D}${sysconfdir}/fonts/conf.d/*.conf
}
