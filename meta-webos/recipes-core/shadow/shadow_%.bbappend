# Copyright (c) 2014-2025 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos4"

PACKAGES =+ "${PN}-gpl"

LICENSE += "& GPL-2.0-only"
LICENSE:${PN}-gpl = "GPL-2.0-only"

RDEPENDS:${PN} += "${PN}-gpl"

FILES:${PN}-gpl = "${base_sbindir}/vipw.shadow ${base_sbindir}/vigr.shadow ${bindir}/su"

ALTERNATIVE:${PN}-base:remove = "su"

#added by TVPLAT-267601
do_install:append() {
    rm -v ${D}${base_bindir}/su
}

# This is as per the security guideline
FAIL_DELAY = "10"
do_install:append() {
    sed -i 's#^FAIL_DELAY.*$#FAIL_DELAY ${FAIL_DELAY}#g' ${D}${sysconfdir}/login.defs
}
