# Copyright (c) 2014-2016 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos2"

PACKAGES =+ "${PN}-gpl"

LICENSE += "& GPL-2.0"
LICENSE_${PN}-gpl = "GPL-2.0"

RDEPENDS_${PN} += "${PN}-gpl"

FILES_${PN}-gpl = "${base_sbindir}/vipw.shadow ${base_sbindir}/vigr.shadow ${bindir}/su"
