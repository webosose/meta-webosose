# Copyright (c) 2019 LG Electronics, Inc.

EXTENDPRAUTO:append = "webosvirt2"

RRECOMMENDS:${PN}:remove = "lxc"

do_install:append() {
    # don't start by default
    sed -i '/\[Install\]/,+1 d' ${D}${systemd_system_unitdir}/containerd.service
}
