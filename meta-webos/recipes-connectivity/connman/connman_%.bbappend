# Copyright (c) 2018-2020 LG Electronics, Inc.

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

EXTENDPRAUTO_append = "webos10"
SYSTEMD_SERVICE_${PN}_remove = "connman.service"

SRC_URI += " \
    file://0001-Add-support-for-the-WPS-PBS-and-PIN-mode.patch \
    file://0002-Set-IPv6-state-same-as-IPV4-on-disconnect.patch \
    file://0003-Fix-for-wifi-network-switching-and-unable-to-connect.patch \
    file://0004-Multiple-wi-fi-networks-are-connected-via-WPS-PIN.patch \
    file://0005-Support-additional-feature-for-tethering.patch \
    file://0006-Support-channel-frequency-of-scanned-AP.patch \
    file://0007-Provide-station-information-when-AP-mode.patch \
    file://0008-Fix-compile-error-regarding-connman_tethering_get_st.patch \
    file://0009-Fix-the-wifi-connection-failure.patch \
"

do_install_append() {
    rm -vf ${D}${systemd_unitdir}/system/connman.service
}
