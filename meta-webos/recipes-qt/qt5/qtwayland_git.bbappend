# Copyright (c) 2013-2021 LG Electronics, Inc.

inherit webos_qt_global

EXTENDPRAUTO_append = "webos21"

# qtwayland-{plugins,qmlplugins} are not used in webOS
RRECOMMENDS_${PN}_remove = "${PN}-plugins ${PN}-qmlplugins"

# TODO: Will be dropped soon once we migrate to a newer meta-qt6
SRCREV = "87cbec8d4a71015219360a7f9a3ccf2a64af8d68"

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

# Upstream-Status: Backport
SRC_URI_append = " \
    file://0001-Enhance-QWaylandQuickHardwareLayer-support.patch \
"
