# Copyright (c) 2019-2022 LG Electronics, Inc.

EXTENDPRAUTO:append = "webosupdater2"

do_compile:append:sota() {
    # to show relative path on prompt for symbolic linked folder (when usrmerge feature)
    if ${@bb.utils.contains('DISTRO_FEATURES', 'usrmerge', 'true', 'false', d)}; then
        sed -i "s/PS1='.*'$/PS1='\\\\u@\\\\h:\$(pwd)\\\\$ '/g" ${WORKDIR}/profile
    fi
}

generate_fstab_entries:append:sota() {
    echo "# mount /media that was not handled by ostree"
    echo "/sysroot/media /media none defaults,bind 0 0"
}
