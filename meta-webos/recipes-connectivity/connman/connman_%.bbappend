# Copyright (c) 2018-2019 LG Electronics, Inc.

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

EXTENDPRAUTO_append = "webos9"
SYSTEMD_SERVICE_${PN}_remove = "connman.service"

SRC_URI += " \
    file://0004-Support-WPS-PBC-and-PIN-mode.patch \
    file://0006-Fix-Unable-to-reconnect-to-same-Wi-Fi.patch \
    file://0007-Fix-for-wifi-network-switching-and-unable-to-connect.patch \
    file://0008-Multiple-wi-fi-networks-are-connected-via-WPS-PIN.patch \
    file://0009-Support-additional-feature-for-tethering.patch \
    file://0010-Support-channel-frequency-of-scanned-AP.patch \
    file://0011-Provide-station-information-when-AP-mode.patch \
    file://0012-Fix-compile-error-regarding-connman_tethering_get_st.patch \
"

do_install_append() {
    rm -vf ${D}${systemd_unitdir}/system/connman.service
}
