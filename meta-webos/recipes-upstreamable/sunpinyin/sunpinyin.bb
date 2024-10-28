# Copyright (c) 2019-2024 LG Electronics, Inc.

SUMMARY = "Pinyin input method library"
AUTHOR = "Pugalendhi Ganesan <pugalendhi.ganesan@lge.com>"
HOMEPAGE = "https://github.com/sunpinyin/sunpinyin"
SECTION = "libs"
LICENSE = "LGPL-2.1-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=0fb040b9b86a214885e3285fe25d74bc"

DEPENDS = "sqlite3"
DEPENDS:append:class-target = " sunpinyin-native"

inherit pkgconfig scons perlnative

BBCLASSEXTEND = "native"

SRCREV = "f39c195db08661e894017507842991a1ef70bedf"
PV = "2.99+3.0.0-rc1+git${SRCPV}"
PR = "r4"

SRC_URI[arpa.md5sum] = "20cdc36b3bd7ec28f7e39c2ebb810421"
SRC_URI[arpa.sha256sum] = "751bab7c55ea93a2cedfb0fbb7eb09f67d4da9c2c55496e5f31eb8580f1d1e2f"
SRC_URI[dict.md5sum] = "2055f50a0f942b49d4417d801388eba5"
SRC_URI[dict.sha256sum] = "af70bc2bcd7af7468495774fed9e3a2de434650119fbc3d3388c2bcf7e0acb01"

SRC_URI =  "git://github.com/sunpinyin/sunpinyin.git;protocol=https;branch=master \
    https://downloads.sourceforge.net/project/open-gram/lm_sc.3gm.arpa-20140820.tar.bz2;name=arpa;subdir=git \
    https://downloads.sourceforge.net/project/open-gram/dict.utf8-20131214.tar.bz2;name=dict;subdir=git \
    file://0001-Change-scon-to-download-open-gram-dictionary.patch \
    file://0002-add-sunpinyin-wrapper-class.patch \
"

S = "${WORKDIR}/git"

do_install:append:class-target() {
    install -d ${D}${libdir}/maliit/plugins/dict
    install -m 755 ${S}/libsunpinyin.so.3.0 ${D}${libdir}/maliit/plugins
    install -m 755 ${S}/Dictionary/pydict_sc.bin ${D}${libdir}/maliit/plugins/dict
    install -m 755 ${S}/Dictionary/lm_sc.t3g ${D}${libdir}/maliit/plugins/dict
}

do_install:append:class-native () {
    install -d                                  ${D}${bindir}
    install -m 755 ${S}/src/genpyt              ${D}${bindir}
    install -m 755 ${S}/src/getwordfreq         ${D}${bindir}
    install -m 755 ${S}/src/idngram_merge       ${D}${bindir}
    install -m 755 ${S}/src/ids2ngram           ${D}${bindir}
    install -m 755 ${S}/src/mmseg               ${D}${bindir}
    install -m 755 ${S}/src/slminfo             ${D}${bindir}
    install -m 755 ${S}/src/slmpack             ${D}${bindir}
    install -m 755 ${S}/src/slmprune            ${D}${bindir}
    install -m 755 ${S}/src/slmseg              ${D}${bindir}
    install -m 755 ${S}/src/slmthread           ${D}${bindir}
    install -m 755 ${S}/src/sunpinyin-dictgen   ${D}${bindir}
    install -m 755 ${S}/src/testvc              ${D}${bindir}
    install -m 755 ${S}/src/tslmendian          ${D}${bindir}
    install -m 755 ${S}/src/tslminfo            ${D}${bindir}
}

FILES:${PN} += "${libdir}/maliit/plugins/"
