# Copyright (c) 2014-2022 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos2"

PACKAGES =+ "${PN}-gpl"

LICENSE += "& GPL-2.0"
LICENSE:${PN}-gpl = "GPL-2.0"

RDEPENDS:${PN} += "${PN}-gpl"

FILES:${PN}-gpl = "${base_sbindir}/vipw.shadow ${base_sbindir}/vigr.shadow ${bindir}/su"
