# Copyright (c) 2019-2023 LG Electronics, Inc.

EXTENDPRAUTO:append = "webosrpi3"
EXTENDPRAUTO:append:sota = ".sota"

do_install:append:sota() {
    install -d ${D}${localstatedir}/rootdirs/mnt/bootpart
}

do_install:append:hardware() {
    if ${@bb.utils.contains('DISTRO_FEATURES', 'sota', 'false', 'true', d)}; then
        sed -i '/dev\/root/s/defaults/defaults,ro/' ${D}${sysconfdir}/fstab
    fi
}

generate_fstab_entries:append:sota() {
    echo "# need to handled boot partition by ostree and service"
    echo "/dev/mmcblk0p1 /mnt/bootpart vfat noatime 0 0"
}
