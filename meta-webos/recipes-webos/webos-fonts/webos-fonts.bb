# Copyright (c) 2017-2024 LG Electronics, Inc.

require ${BPN}.inc

WEBOS_VERSION = "1.0.0-15_a24f371a4c667c36028514c6b708710867ffc6ba"
PR = "${INC_PR}.2"

inherit webos_arch_indep
inherit webos_enhanced_submissions
inherit webos_public_repo

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

do_install() {
    install -d ${D}${datadir}/fonts
    install -v -m 644 Miso/*.ttf ${D}${datadir}/fonts
    install -v -m 644 Museo/*.ttf ${D}${datadir}/fonts
    install -v -m 644 NotoSans/*.ttf NotoSans/*.otf ${D}${datadir}/fonts
    install -v -m 644 NotoSansOld/*.ttf ${D}${datadir}/fonts
}
