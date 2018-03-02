# Copyright (c) 2013 LG Electronics, Inc.

SUMMARY = "XULRunner Open Source Project"
DESCRIPTION = "SDK to make NPAPI plugins"
SECTION = "libs/network"
LICENSE = "MPL-1.1"
LIC_FILES_CHKSUM =  "file://include/npapi.h;md5=37ece98d0f0db87536ea205c268eb946"

DEPENDS = ""

PR = "r0"

SRC_URI = "http://ftp.mozilla.org/pub/mozilla.org/xulrunner/releases/${PV}/sdk/xulrunner-${PV}.en-US.linux-i686.sdk.tar.bz2"
SRC_URI[md5sum] = "2fc380eb06874a1051468f84328bfddc"
SRC_URI[sha256sum] = "42812d23ebe0fda002926ea5820aa665ad76f5bb3b0fb7d723fba80ce9b6baa8"

S = "${WORKDIR}/xulrunner-sdk"

do_install() {
    install -d ${D}${includedir}/xulrunner-sdk
    cp -rf ${S}/include/ ${D}${includedir}/xulrunner-sdk/
}
