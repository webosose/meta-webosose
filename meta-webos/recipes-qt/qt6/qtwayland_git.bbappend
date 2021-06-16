# Copyright (c) 2013-2021 LG Electronics, Inc.

inherit webos_qt_global

EXTENDPRAUTO_append = "webos23"

# qtwayland-{plugins,qmlplugins} are not used in webOS
RRECOMMENDS_${PN}_remove = "${PN}-plugins ${PN}-qmlplugins"

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"
