# Copyright (c) 2016-2019 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos4"

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

# Patches based on 5.9.meta-qt5.8
SRC_URI += " \
    file://0001-Add-QMediaPlayerControl-accessor.patch \
"

# until pseudo is completely fixed
# PLAT-48507 pseudo: random package_qa failures
INSANE_SKIP_${PN}-dev += "host-user-contaminated"
INSANE_SKIP_${PN}-plugins += "host-user-contaminated"
INSANE_SKIP_${PN}-qmlplugins += "host-user-contaminated"
INSANE_SKIP_${PN}-mkspecs += "host-user-contaminated"
