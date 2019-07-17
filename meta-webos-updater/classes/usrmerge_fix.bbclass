# Copyright (c) 2019 LG Electronics, Inc.

# move /lib, /bin, /sbin to /usr
do_install_append_sota() {
    if ${@bb.utils.contains('DISTRO_FEATURES', 'usrmerge', 'true', 'false', d)}; then
        TARGETS='/lib /bin /sbin'
        for target in ${TARGETS}; do
            if [ -d ${D}${target} ]; then
                install -d ${D}${prefix}${target}
                cp -rf ${D}${target}/* ${D}${prefix}${target} 2> /dev/null || true
                rm -rf ${D}${target}
            fi
        done
    fi
}