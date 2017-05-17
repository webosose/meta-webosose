# Copyright (c) 2014-2017 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos5"

PACKAGES =+ "${PN}-gpl"
LICENSE += "& GPL-2.0"
LICENSE_${PN}-gpl = "GPL-2.0"

# using "dbus" instead of "${PN}" due to:
# WARNING: Variable key RDEPENDS_${PN} ( ${PN}-gpl) replaces original key RDEPENDS_dbus
# (${@bb.utils.contains('DISTRO_FEATURES', 'ptest', 'dbus-ptest-ptest', '', d)}).
RDEPENDS_dbus += "${PN}-gpl"

FILES_${PN}-gpl = " \
    ${bindir}/dbus-cleanup-sockets \
    ${bindir}/dbus-daemon \
    ${bindir}/dbus-monitor \
    ${bindir}/dbus-send \
    ${bindir}/dbus-uuidgen \
"
