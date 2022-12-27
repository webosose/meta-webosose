# Copyright (c) 2019-2023 LG Electronics, Inc.

EXTENDPRAUTO:append = "webosrpi2"

do_install:append:sota() {
    install -d ${D}${localstatedir}/rootdirs/mnt/bootpart
}

generate_fstab_entries:append:sota() {
    echo "# need to handled boot partition by ostree and service"
    echo "/dev/mmcblk0p1 /mnt/bootpart vfat noatime 0 0"
}
