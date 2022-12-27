# Copyright (c) 2019-2023 LG Electronics, Inc.

EXTENDPRAUTO:append = "webosvirt4"

VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS:${PN}-contrib:append:class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS:${PN}-contrib:remove:class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"

# required kernel modules
RRECOMMENDS:${PN}:append = " \
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

do_install:append() {
    # don't start by default
    sed -i '/\[Install\]/,+1 d' ${D}${systemd_system_unitdir}/docker.service
    sed -i '/\[Install\]/,+1 d' ${D}${systemd_system_unitdir}/docker.socket

    # create symlink for /etc/docker because of it's on the R/O partition.
    if ${@bb.utils.contains('IMAGE_FEATURES','read-only-rootfs','true','false',d)}; then
        rm -rf ${D}/${sysconfdir}/docker
        install -d ${D}/${sysconfdir}
        ln -sf /var/lib/docker ${D}/${sysconfdir}/docker
    fi
}
