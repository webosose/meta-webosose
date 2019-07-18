# Copyright (c) 2019 LG Electronics, Inc.

EXTENDPRAUTO_append = "${@bb.utils.contains('DISTRO_FEATURES', 'smack', 'smack1', '', d)}"

do_install[postfuncs] += "${@bb.utils.contains('DISTRO_FEATURES', 'smack', 'set_tmpfs_star', '', d)}"

set_tmpfs_star () {
    fstab="${D}/${sysconfdir}/fstab"
    awk '$1 == "tmpfs" {$4=$4",smackfsroot=*"} {print}' $fstab > "$fstab.tmp" && mv $fstab.tmp $fstab
}
