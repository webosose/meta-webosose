# Copyright (c) 2017-2019 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos4"

VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS_${PN}-kernel-install_append_class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS_${PN}-kernel-install_remove_class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"
RDEPENDS_${PN}-ptest_append_class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS_${PN}-ptest_remove_class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"

RDEPENDS_${PN}_remove = "update-rc.d"

PACKAGECONFIG_remove = " \
    networkd    \
    resolved    \
"
# By default systemd's Predictable Network Interface Names policy configured for qemu
# Currently we don't support this policy in qemu, so removing from systemd's configuration
do_install_append_qemuall() {
    rm -rf ${D}/${base_libdir}/systemd/network/99-default.link
}
