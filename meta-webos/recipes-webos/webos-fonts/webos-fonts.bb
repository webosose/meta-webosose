# Copyright (c) 2017-2023 LG Electronics, Inc.

require ${BPN}.inc

WEBOS_VERSION = "1.0.0-14_d515a96cc786e1d233dd70b9b462044861a5042d"
PR = "${INC_PR}.1"

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
}
