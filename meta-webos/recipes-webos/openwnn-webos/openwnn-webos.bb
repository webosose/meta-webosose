# Copyright (c) 2019-2023 LG Electronics, Inc.

SUMMARY = "A Japanese IME library (input method editor for typing Japanese)"
AUTHOR = "Guruprasad KN <guruprasad.kn@lge.com>"
SECTION = "libs"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://COPYING;md5=34e549453b3e73c1d635e93b4a01b96b \
    file://oss-pkg-info.yaml;md5=9e866a0c61ba2b36863c702e9a4c9163 \
"

inherit pkgconfig
inherit webos_enhanced_submissions
inherit webos_public_repo

WEBOS_VERSION = "1.0.0-3_1f3545329b357facd864a42d47a75389e932ebbe"
PR = "r1"

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

do_install:append() {
    install -d  ${D}${libdir}/maliit/plugins
    install -m 755 ${S}/libWnnJpn.so ${D}${libdir}/maliit/plugins
}

TARGET_CC_ARCH += "${LDFLAGS}"
FILES:${PN} += "${libdir}/maliit/plugins/"

