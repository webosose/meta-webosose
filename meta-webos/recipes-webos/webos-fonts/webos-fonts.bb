# Copyright (c) 2017-2020 LG Electronics, Inc.

require ${BPN}.inc

WEBOS_VERSION = "1.0.0-5_b2831cddd9c37ccbb1946178fc137b115e6430fe"
PR = "r0"

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
