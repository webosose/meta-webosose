# Copyright (c) 2015-2019 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos6"

DEPENDS += "qtdeclarative-native"

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

# Patches based on 5.9.meta-qt5.8
SRC_URI += " \
    file://0001-Fix-Do-not-resize-dialog-parent-window.patch \
    file://0002-Check-if-decoration-allowed-for-a-dialog.patch \
"

# until pseudo is completely fixed
# PLAT-48507 pseudo: random package_qa failures
INSANE_SKIP_${PN}-qmlplugins += "host-user-contaminated"
INSANE_SKIP_${PN}-qmldesigner += "host-user-contaminated"
