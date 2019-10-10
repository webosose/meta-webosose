# Copyright (c) 2015-2019 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos6"

DEPENDS += "qtdeclarative-native"

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

# Patches based on 5.12.meta-qt5.3
SRC_URI += " \
    file://0001-Fix-Do-not-resize-dialog-parent-window.patch \
    file://0002-Check-if-decoration-allowed-for-a-dialog.patch \
"
