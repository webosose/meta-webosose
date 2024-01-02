# Copyright (c) 2019-2024 LG Electronics, Inc.

EXTENDPRAUTO:append = "webosrpi3"

do_install:append:hardware() {
    sed -i '/dev\/root/s/defaults/defaults,ro/' ${D}${sysconfdir}/fstab
}
