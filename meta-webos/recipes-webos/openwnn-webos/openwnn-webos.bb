# Copyright (c) 2019 LG Electronics, Inc.

SUMMARY = "A Japanese IME library (input method editor for typing Japanese)"
AUTHOR = "Pugalendhi Ganesan <pugalendhi.ganesan@lge.com>"
SECTION = "libs"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://COPYING;md5=34e549453b3e73c1d635e93b4a01b96b"

inherit pkgconfig
inherit webos_enhanced_submissions
inherit webos_public_repo

WEBOS_VERSION = "1.0.0-1_4eb6c58d44f596c6ac02d5e5e23a097a7ab41dbb"
PR = "r0"

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

do_install_append() {
    install -d  ${D}${libdir}/maliit/plugins
    install -m 755 ${S}/libWnnJpn.so ${D}${libdir}/maliit/plugins
}

TARGET_CC_ARCH += "${LDFLAGS}"
FILES_${PN} += "${libdir}/maliit/plugins/"

