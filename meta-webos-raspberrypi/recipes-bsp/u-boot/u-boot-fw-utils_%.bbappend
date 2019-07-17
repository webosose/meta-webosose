# Copyright (c) 2019 LG Electronics, Inc.

EXTENDPRAUTO_append = "webosrpi1"

do_install_append_sota() {
    echo "/mnt/bootpart/uboot.env 0x0000 0x4000" > ${D}${sysconfdir}/fw_env.config
}
