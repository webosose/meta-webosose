# Copyright (c) 2019 LG Electronics, Inc.

EXTENDPRAUTO_append = "${@bb.utils.contains('DISTRO_FEATURES', 'smack', 'smack1', '', d)}"

do_install[postfuncs] += "${@bb.utils.contains('DISTRO_FEATURES', 'smack', 'set_connman_mac_override', '', d)}"

set_connman_mac_override () {
    service="${D}/${sysconfdir}/systemd/system/connman.service"
    if [ -f "$service" ]; then
        if grep -q '^CapabilityBoundingSet=' "$service"; then
            sed -i -e 's/^CapabilityBoundingSet=/CapabilityBoundingSet=CAP_MAC_OVERRIDE /' "$service"
        else
            echo "CapabilityBoundingSet=CAP_MAC_OVERRIDE" >> "$service"
        fi
    fi
}

