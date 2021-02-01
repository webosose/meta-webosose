# Copyright (c) 2015-2021 LG Electronics, Inc.

AUTHOR  = "Viesturs Zarins <viesturs.zarins@lge.com>"
EXTENDPRAUTO_append = "webos2"

CRASHD = "crashd"
# crashd depends on libunwind which depends on blacklisted libatomics-ops when building for armv[45]"
CRASHD_armv4 = ""
CRASHD_armv5 = ""

# In webos, systemd-coredump will replace the crashd.
RDEPENDS_${PN} += "${@bb.utils.contains('DISTRO', 'webos', '', '${CRASHD}', d)}"

inherit webos_machine_impl_dep
inherit webos_prerelease_dep

do_install_append_hardware() {

    echo "" >> ${D}${sysconfdir}/sysctl.conf
    echo "# Additional Security Settings" >> ${D}${sysconfdir}/sysctl.conf

    # Close various vulnerabilities in the network stack
    sed -i 's/^.*net.ipv4.conf.all.accept_redirects.*/net.ipv4.conf.all.accept_redirects = 0/g' ${D}${sysconfdir}/sysctl.conf
    sed -i 's/^.*net.ipv4.conf.all.accept_source_route.*/net.ipv4.conf.all.accept_source_route = 0/g' ${D}${sysconfdir}/sysctl.conf
    sed -i 's/^.*net.ipv4.conf.all.secure_redirects.*/net.ipv4.conf.all.secure_redirects = 0/g' ${D}${sysconfdir}/sysctl.conf
    echo "net.ipv4.conf.default.accept_redirects = 0" >> ${D}${sysconfdir}/sysctl.conf
    echo "net.ipv4.conf.default.accept_source_route = 0" >> ${D}${sysconfdir}/sysctl.conf
    echo "net.ipv4.conf.default.secure_redirects = 0" >> ${D}${sysconfdir}/sysctl.conf

    # Protect against icmp attacks
    sed -i 's/^.*net.ipv4.icmp_echo_ignore_broadcasts.*/net.ipv4.icmp_echo_ignore_broadcasts = 1/g' ${D}${sysconfdir}/sysctl.conf
    sed -i 's/^.*net.ipv4.icmp_ignore_bogus_error_responses.*/net.ipv4.icmp_ignore_bogus_error_responses = 1/g' ${D}${sysconfdir}/sysctl.conf

    # For proper network operation during syn flood attacks
    # TODO Re-enable this option if webos devices or kernel gets compiled with CONFIG_SYN_COOKIES
    #sed -i 's/^.*net.ipv4.tcp_syncookies.*/net.ipv4.tcp_syncookies = 1/g' ${D}${sysconfdir}/sysctl.conf

    # Enable reverse path source validation
    sed -i 's/^.*net.ipv4.conf.all.rp_filter.*/net.ipv4.conf.all.rp_filter = 1/g' ${D}${sysconfdir}/sysctl.conf
    sed -i 's/^.*net.ipv4.conf.default.rp_filter.*/net.ipv4.conf.default.rp_filter = 1/g' ${D}${sysconfdir}/sysctl.conf

    # Prevent device from acting as router
    sed -i 's/^.*net.ipv4.ip_forward.*/net.ipv4.ip_forward = 0/g' ${D}${sysconfdir}/sysctl.conf
    sed -i 's/^.*net.ipv4.conf.all.send_redirects.*/net.ipv4.conf.all.send_redirects = 0/g' ${D}${sysconfdir}/sysctl.conf
    echo "net.ipv4.conf.default.send_redirects = 0" >> ${D}${sysconfdir}/sysctl.conf

    # For system monitoring
    sed -i 's/^.*net.ipv4.conf.all.log_martians.*/net.ipv4.conf.all.log_martians = 1/g' ${D}${sysconfdir}/sysctl.conf

    # linux-rockhopper is not configured to enable IPv6.

    if ${@oe.utils.conditional('WEBOS_DISTRO_PRERELEASE', 'devel', 'true', 'false', d)}; then
        echo "" >> ${D}${sysconfdir}/sysctl.conf
        echo "# Generate core dumps" >> ${D}${sysconfdir}/sysctl.conf
        if ${@oe.utils.conditional('DISTRO', 'webos', 'true', 'false', d)} ; then
            echo "kernel.core_pattern = |/lib/systemd/systemd-coredump %P %u %g %s %t %c %h %e" >> ${D}${sysconfdir}/sysctl.conf
        else
            echo "kernel.core_pattern = |/usr/sbin/save-core-dump.sh %e %p" >> ${D}${sysconfdir}/sysctl.conf
        fi
        echo "fs.suid_dumpable = 1" >> ${D}${sysconfdir}/sysctl.conf
    fi
}
