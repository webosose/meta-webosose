# Copyright (c) 2018-2022 LG Electronics, Inc.

FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

EXTENDPRAUTO:append = "webos20"
SYSTEMD_SERVICE:${PN}:remove = "connman.service"

SRC_URI += " \
    file://0001-Add-support-for-the-WPS-PBS-and-PIN-mode.patch \
    file://0002-Set-IPv6-state-same-as-IPV4-on-disconnect.patch \
    file://0003-Fix-for-wifi-network-switching-and-unable-to-connect.patch \
    file://0004-Multiple-wi-fi-networks-are-connected-via-WPS-PIN.patch \
    file://0007-Provide-station-information-when-AP-mode.patch \
    file://0008-Fix-compile-error-regarding-connman_tethering_get_st.patch \
    file://0009-Fix-the-wifi-connection-failure.patch \
    file://0010-Implementing-the-configuration-options-related-to-P2.patch \
    file://0011-Read-WpaSupplicantConfigFile-from-main-configuration.patch \
    file://0013-Added-new-method-SetDefault-for-Interface-net.connma.patch \
    file://0014-Pick-gateway-from-gateway_hash-if-it-is-not-updated-.patch \
    file://0015-Add-CIDR-prefix-length-as-ipv4-property.patch \
    file://0017-Revert-storage-Remove-unused-__connman_storage_open_.patch \
    file://0019-Fix-connman-crash.patch \
    file://0020-Fix-connman-crash-when-interface-removed.patch \
    file://0021-Fix-bug-p2p-findservice-luna-fail.patch \
"

do_install:append() {
    rm -vf ${D}${systemd_unitdir}/system/connman.service
}
