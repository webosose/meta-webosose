# Copyright (c) 2019 LG Electronics, Inc.

EXTENDPRAUTO_append = "webosvirt1"

VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS_${PN}-contrib_append_class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS_${PN}-contrib_remove_class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"

# required kernel modules
RRECOMMENDS_${PN}_append = " \
    kernel-module-br-netfilter \
    kernel-module-ebt-dnat \
    kernel-module-ip-vs \
    kernel-module-nf-defrag-ipv6 \
    kernel-module-nfnetlink \
    kernel-module-openvswitch \
    kernel-module-veth \
    kernel-module-xt-addrtype \
    kernel-module-xt-conntrack \
    kernel-module-xt-nat \
"

# don't start by default
do_install_append() {
    sed -i '/\[Install\]/,+1 d' ${D}${systemd_system_unitdir}/docker.service
}
