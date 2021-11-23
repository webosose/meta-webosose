# Copyright (c) 2017-2021 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos9"
FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

SRC_URI_append_webos = " \
    file://0001-systemd-oomd-depend-on-swap-on.patch \
    file://0002-Add-webos-interface.patch \
    file://0003-systemd-oomd-modify-oomd.conf.patch \
    file://0004-systemd-oomd-to-some.patch \
    file://0005-systemd-oomd-change-duration.patch \
"

inherit webos_prerelease_dep

VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS_${PN}-kernel-install_append_class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS_${PN}-kernel-install_remove_class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"
RDEPENDS_${PN}-ptest_append_class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS_${PN}-ptest_remove_class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"

RDEPENDS_${PN}_remove = "update-rc.d"

PACKAGECONFIG_remove = " \
    networkd    \
    resolved    \
    nss-resolve \
    timedated   \
    timesyncd   \
"

PACKAGECONFIG_append = " \
    ${@oe.utils.conditional('DISTRO', 'webos', 'oomd', '', d)} \
    ${@oe.utils.conditional('DISTRO', 'webos', 'cgroupv2', '', d)} \
    ${@oe.utils.conditional('WEBOS_DISTRO_PRERELEASE', 'devel', 'coredump', '', d)} \
    ${@oe.utils.conditional('WEBOS_DISTRO_PRERELEASE', 'devel', 'elfutils', '', d)} \
"

FILES_${PN} += "${@oe.utils.conditional('DISTRO', 'webos','${datadir}/dbus-1/system.d/com.webos.MemoryManager1.conf', '', d)}"

# By default systemd's Predictable Network Interface Names policy configured for qemu
# Currently we don't support this policy in qemu, so removing from systemd's configuration
do_install_append_qemuall() {
    rm -rf ${D}/${base_libdir}/systemd/network/99-default.link
}
