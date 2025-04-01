# Copyright (c) 2013-2025 LG Electronics, Inc.

inherit webos_machine_impl_dep

FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

EXTENDPRAUTO:append = "webos10"

# move startup scripts in different packages
PACKAGES =+ "${PN}-sysvinit"
FILES:${PN}-sysvinit = "${sysconfdir}/init.d"

# for update-rc.bbclass to know where to put postinst/prerm
UPDATERCPN = "${PN}-sysvinit"

inherit webos_systemd
WEBOS_SYSTEMD_SERVICE = "dropbear.service"

PACKAGECONFIG:append = " system-libtom"

do_install:append() {
    install -d ${D}${sysconfdir}/systemd/system
    ln -sf /dev/null ${D}/${sysconfdir}/systemd/system/dropbear.socket

    # If initscripts runtime package is not set, we need to enable the dropbear service unit.
    if ${@oe.utils.conditional('VIRTUAL-RUNTIME_initscripts', '', 'true', 'false', d)}; then
        install -d ${D}/${sysconfdir}/systemd/system/multi-user.target.wants
        ln -sf ../dropbear.service ${D}/${sysconfdir}/systemd/system/multi-user.target.wants/dropbear.service
    fi
}

# Remove runtime dependency on separate dropbear-upstart package with Upstart 0.3 job file.
RDEPENDS:${PN} = ""
RDEPENDS:${PN}:emulator = "dhcpcd"

FILES:${PN} += "${base_libdir} ${systemd_system_unitdir}"
