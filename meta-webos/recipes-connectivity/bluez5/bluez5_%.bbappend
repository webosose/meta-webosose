# Copyright (c) 2018 LG Electronics, Inc.

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

EXTENDPRAUTO_append = "webos1"

SRC_URI += " \
    file://0001-Fix-advertise-time-out-when-default-is-set-to-zero.patch \
    file://0002-Send-disconnect-signal-on-remote-device-disconnect.patch \
    file://0003-Fetching-device-type-like-BLE-BREDR-from-bluez.patch \
    file://0004-device-Fix-Connect-and-ConnectProfile-returing-InPro.patch \
    file://0005-Fix-Gatt-connect-when-device-address-type-is-BDADDR_.patch \
    file://main.conf \
"

do_install_append () {
    install -v -m 0644  ${WORKDIR}/main.conf ${D}${sysconfdir}/bluetooth/
}
