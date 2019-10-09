# Copyright (c) 2019 LG Electronics, Inc.

EXTENDPRAUTO_append = "${@bb.utils.contains('DISTRO_FEATURES', 'smack', 'smack1', '', d)}"

do_install[postfuncs] += "${@bb.utils.contains('DISTRO_FEATURES', 'smack', 'set_wam_process_label', '', d)}"

set_wam_process_label () {
    service="${D}/${sysconfdir}/systemd/system/webapp-mgr.service"
    if [ -f "$service" ]; then
         echo "SmackProcessLabel=webOS::WAM" >>  "$service"
    fi
}
