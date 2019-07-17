# Copyright (c) 2019 LG Electronics, Inc.

EXTENDPRAUTO_append = "webosrpi1"

do_install_append_sota() {
    install -d ${D}/mnt/bootpart
}

generate_fstab_entries_append_sota() {
    echo "# need to handled boot partition by ostree and service"
    echo "/dev/mmcblk0p1 /mnt/bootpart vfat noatime 0 0"
}
