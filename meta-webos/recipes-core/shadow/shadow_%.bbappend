# Copyright (c) 2014-2023 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos2"

PACKAGES =+ "${PN}-gpl"

LICENSE += "& GPL-2.0-only"
LICENSE:${PN}-gpl = "GPL-2.0-only"

RDEPENDS:${PN} += "${PN}-gpl"

FILES:${PN}-gpl = "${base_sbindir}/vipw.shadow ${base_sbindir}/vigr.shadow ${bindir}/su"
