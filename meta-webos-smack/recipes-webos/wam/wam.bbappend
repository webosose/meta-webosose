# Copyright (c) 2019-2020 LG Electronics, Inc.

EXTENDPRAUTO:append = "${@bb.utils.contains('DISTRO_FEATURES', 'smack', 'smack2', '', d)}"

do_install[postfuncs] += "${@bb.utils.contains('DISTRO_FEATURES', 'smack', 'set_wam_process_label', '', d)}"

set_wam_process_label () {
    service="${D}${SYSTEMD_INSTALL_PATH}/webapp-mgr.service"
    if [ -f "$service" ]; then
         echo "SmackProcessLabel=webOS::WAM" >>  "$service"
    fi
}
