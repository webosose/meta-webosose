# Copyright (c) 2019-2025 LG Electronics, Inc.

EXTENDPRAUTO:append:rpi = "webosrpi4"

do_install:append:rpi() {
    sed -i '/dev\/root/s/defaults/defaults,ro/' ${D}${sysconfdir}/fstab
}
