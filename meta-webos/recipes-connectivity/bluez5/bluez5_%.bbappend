# Copyright (c) 2018-2020 LG Electronics, Inc.

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

EXTENDPRAUTO_append = "webos18"

RRECOMMENDS_${PN} += " \
    glibc-gconv-utf-16 \
    glibc-gconv-utf-32 \
"

SRC_URI += " \
    file://0001-Fix-advertise-time-out-when-default-is-set-to-zero.patch \
    file://0002-Send-disconnect-signal-on-remote-device-disconnect.patch \
    file://0003-Fetching-device-type-like-BLE-BREDR-from-bluez.patch \
    file://0004-Fix-Gatt-connect-when-device-address-type-is-BDADDR_.patch \
    file://0005-Fix-obexd-segmentation-fault.patch \
    file://0006-Use-system-bus-instead-of-session-for-obexd.patch \
    file://0007-Implementation-to-get-connected-profiles-uuids.patch \
    file://0008-recievePassThrough-commad-support-required-for-webos.patch \
    file://0009-Added-dbus-signal-for-MediaPlayRequest.patch \
    file://0010-avrcp-getting-remote-device-features-list.patch \
    file://0011-Fix-add-service-failure.patch \
    file://0014-Fix-volume-property-not-able-to-set.patch \
    file://0015-Fix-volume-level-notification-not-appearing-after-127.patch \
    file://0016-Support-enabling-avdtp-delayReport.patch \
    file://0017-Subject-PATCH-Implementation-to-get-connectedUuid-s-.patch \
    file://0018-Fix-for-updating-connected-uuids-when-profile-is-disconnected.patch \
    file://0019-Fix-device-getStatus-not-updated-when-unpaired.patch \
    file://0020-AVRCP-getting-supported-notification-events.patch \
    file://0019-Set-default-pairing-capability-as-NoInputNoOutput.patch \
    file://0021-Modified-MapInstanceName-MapInstanceProperties-parsi.patch \
    file://0022-Enabled-EMAIL-support-based-on-MAPInstance-Name.patch \
    file://0023-Disabling-DB-Hash-for-Gatt.patch \
    file://main.conf \
    file://brcm43438.service \
    file://obex.service \
"

SRC_URI_append_raspberrypi4 = " \
    file://blacklistbtusb.conf \
"

do_install_append () {
    install -d ${D}${sysconfdir}/systemd/system
    install -v -m 0644  ${WORKDIR}/main.conf ${D}${sysconfdir}/bluetooth/
    install -v -m 0644  ${WORKDIR}/obex.service ${D}${sysconfdir}/systemd/system/
}

do_install_append_raspberrypi4 () {
    install -d  ${D}${sysconfdir}/modprobe.d
    install -m 644 ${WORKDIR}/blacklistbtusb.conf  ${D}${sysconfdir}/modprobe.d/blacklistbtusb.conf
}

FILES_${PN}_append_raspberrypi4 = " ${sysconfdir}/modprobe.d/*"
