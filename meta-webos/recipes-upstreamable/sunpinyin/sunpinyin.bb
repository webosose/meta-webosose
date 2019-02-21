# Copyright (c) 2019 LG Electronics, Inc.

SUMMARY = "Pinyin input method library"
AUTHOR = "pugalendhi Ganesan <pugalendhi.ganesan@lge.com>"
HOMEPAGE = "https://github.com/sunpinyin/sunpinyin"
SECTION = "libs"
LICENSE = "LGPLv2.1"
LIC_FILES_CHKSUM = "file://COPYING;md5=0fb040b9b86a214885e3285fe25d74bc"

DEPENDS = "sqlite3"
DEPENDS_append_class-target = " sunpinyin-native"

inherit pkgconfig scons

BBCLASSEXTEND = "native"

SRCREV = "e3c122ed3bf6274fc73042843243e1fb168dfc7e"
PV = "2.99+3.0.0-rc1+git${SRCPV}"
PR = "r0"

SRC_URI[arpa.md5sum] = "20cdc36b3bd7ec28f7e39c2ebb810421"
SRC_URI[arpa.sha256sum] = "751bab7c55ea93a2cedfb0fbb7eb09f67d4da9c2c55496e5f31eb8580f1d1e2f"
SRC_URI[dict.md5sum] = "2055f50a0f942b49d4417d801388eba5"
SRC_URI[dict.sha256sum] = "af70bc2bcd7af7468495774fed9e3a2de434650119fbc3d3388c2bcf7e0acb01"

SRC_URI =  "git://github.com/sunpinyin/sunpinyin.git;protocol=https \
    file://0001-sunpinyin-dictgen.mk.in-completely-abandon-now-defun.patch \
    file://0002-work-around-multiple-monitors-setup.patch \
    file://0003-Portability-fix-for-NetBSD-iconv.patch \
    file://0004-Make-the-.pc-output-reproducible.patch \
    file://0005-sunpinyin-dictgen.mk.in-use-HTTPS-mirrors-to-avoid-M.patch \
    file://0006-Change-scon-to-download-open-gram-dictionary.patch \
    file://0007-add-sunpinyin-wrapper-class.patch \
    https://downloads.sourceforge.net/project/open-gram/lm_sc.3gm.arpa-20140820.tar.bz2;name=arpa \
    https://downloads.sourceforge.net/project/open-gram/dict.utf8-20131214.tar.bz2;name=dict \
"

S = "${WORKDIR}/git"

do_install_append_class-target() {
    install -d  ${D}${libdir}/maliit/plugins/dict
    install -m 755 ${S}/libsunpinyin.so.3.0 ${D}${libdir}/maliit/plugins
    install -m 755 ${S}/Dictionary/pydict_sc.bin ${D}${libdir}/maliit/plugins/dict
    install -m 755 ${S}/Dictionary/lm_sc.t3g ${D}${libdir}/maliit/plugins/dict
}

do_install_append_class-native () {
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

FILES_${PN} += "${libdir}/maliit/plugins/"
