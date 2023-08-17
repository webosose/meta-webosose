# Copyright (c) 2013-2023 LG Electronics, Inc.

inherit webos_machine_impl_dep

FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

EXTENDPRAUTO:append = "webos9"

# (In the emulator) our openssh is installed in /opt prefix, set the sftp path
# this overrides default value set in oe-core's dropbear.inc
CFLAGS:append:emulator = " -DSFTPSERVER_PATH=\\"/opt/openssh/lib/openssh/sftp-server\\""

# move startup scripts in different packages
PACKAGES =+ "${PN}-sysvinit"
FILES:${PN}-sysvinit = "${sysconfdir}/init.d"

# for update-rc.bbclass to know where to put postinst/prerm
UPDATERCPN = "${PN}-sysvinit"

SRC_URI += "file://dropbear.service"

PACKAGECONFIG:append = " system-libtom"

do_install:append() {
    install -d ${D}${sysconfdir}/systemd/system
    install -m 0644 ${WORKDIR}/dropbear.service ${D}${sysconfdir}/systemd/system/
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
