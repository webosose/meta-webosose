# Copyright (c) 2017-2022 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos9"
FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

SRC_URI:append:webos = " \
    file://0001-systemd-oomd-depend-on-swap-on.patch \
    file://0002-Add-webos-interface.patch \
    file://0003-systemd-oomd-modify-oomd.conf.patch \
    file://0004-systemd-oomd-to-some.patch \
    file://0005-systemd-oomd-change-duration.patch \
"

inherit webos_prerelease_dep

VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS:${PN}-kernel-install:append:class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS:${PN}-kernel-install:remove:class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"
RDEPENDS:${PN}-ptest:append:class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS:${PN}-ptest:remove:class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"

RDEPENDS:${PN}:remove = "update-rc.d"

PACKAGECONFIG:remove = " \
    networkd    \
    resolved    \
    nss-resolve \
    timedated   \
    timesyncd   \
"

PACKAGECONFIG:append = " \
    ${@oe.utils.conditional('DISTRO', 'webos', 'oomd', '', d)} \
    ${@oe.utils.conditional('DISTRO', 'webos', 'cgroupv2', '', d)} \
    ${@oe.utils.conditional('WEBOS_DISTRO_PRERELEASE', 'devel', 'coredump', '', d)} \
    ${@oe.utils.conditional('WEBOS_DISTRO_PRERELEASE', 'devel', 'elfutils', '', d)} \
"

FILES:${PN} += "${@oe.utils.conditional('DISTRO', 'webos','${datadir}/dbus-1/system.d/com.webos.MemoryManager1.conf', '', d)}"

# By default systemd's Predictable Network Interface Names policy configured for qemu
# Currently we don't support this policy in qemu, so removing from systemd's configuration
do_install:append:qemuall() {
    rm -rf ${D}/${base_libdir}/systemd/network/99-default.link
}
