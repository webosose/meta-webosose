# Copyright (c) 2018-2019 LG Electronics, Inc.

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

EXTENDPRAUTO_append = "webos5"

RRECOMMENDS_${PN} += " \
    glibc-gconv-utf-16 \
    glibc-gconv-utf-32 \
"

SRC_URI += " \
    file://0001-Fix-advertise-time-out-when-default-is-set-to-zero.patch \
    file://0002-Send-disconnect-signal-on-remote-device-disconnect.patch \
    file://0003-Fetching-device-type-like-BLE-BREDR-from-bluez.patch \
    file://0004-device-Fix-Connect-and-ConnectProfile-returing-InPro.patch \
    file://0005-Fix-Gatt-connect-when-device-address-type-is-BDADDR_.patch \
    file://0006-Fix-obexd-segmentation-fault.patch \
    file://0007-Use-system-bus-instead-of-session-for-obexd.patch \
    file://0008-Implementation-to-get-connected-profiles-uuids.patch \
    file://0009-recievePassThrough-commad-support-required-for-webos.patch \
    file://main.conf \
    file://brcm43438.service \
"

do_install_append () {
    install -v -m 0644  ${WORKDIR}/main.conf ${D}${sysconfdir}/bluetooth/
}
