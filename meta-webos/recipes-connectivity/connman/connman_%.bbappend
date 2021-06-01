# Copyright (c) 2018-2021 LG Electronics, Inc.

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

EXTENDPRAUTO_append = "webos17"
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
    file://0010-Implementing-the-configuration-options-related-to-P2.patch \
    file://0011-Read-WpaSupplicantConfigFile-from-main-configuration.patch \
    file://0012-Send-properties-changed-signal-in-case-of-interface-.patch \
    file://0013-Added-new-method-SetDefault-for-Interface-net.connma.patch \
    file://0014-Pick-gateway-from-gateway_hash-if-it-is-not-updated-.patch \
    file://0015-Add-CIDR-prefix-length-as-ipv4-property.patch \
    file://0016-Add-p2p-changes.patch \
    file://0017-Revert-storage-Remove-unused-__connman_storage_open_.patch \
    file://0018-Fix-crash-in-p2p-addservice-and-p2p-settethering.patch \
    file://0019-Fix-connman-crash.patch \
    file://0020-Fix-connman-crash-when-interface-removed.patch \
"

do_install_append() {
    rm -vf ${D}${systemd_unitdir}/system/connman.service
}
