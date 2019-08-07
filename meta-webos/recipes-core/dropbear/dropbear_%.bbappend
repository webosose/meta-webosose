# Copyright (c) 2013-2019 LG Electronics, Inc.

inherit webos_machine_impl_dep

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

EXTENDPRAUTO_append = "webos7"

# (In the emulator) our openssh is installed in /opt prefix, set the sftp path
# this overrides default value set in oe-core's dropbear.inc
CFLAGS_append_emulator = " -DSFTPSERVER_PATH=\\"/opt/openssh/lib/openssh/sftp-server\\""

# move startup scripts in different packages
PACKAGES =+ "${PN}-sysvinit"
FILES_${PN}-sysvinit = "${sysconfdir}/init.d"

# for update-rc.bbclass to know where to put postinst/prerm
UPDATERCPN = "${PN}-sysvinit"

SRC_URI += "file://dropbear.service"

do_install_append() {
    install -d ${D}${sysconfdir}/systemd/system
    install -m 0644 ${WORKDIR}/dropbear.service ${D}${sysconfdir}/systemd/system/
    ln -sf /dev/null ${D}/${sysconfdir}/systemd/system/dropbear.socket
}

# Remove runtime dependency on separate dropbear-upstart package with Upstart 0.3 job file.
RDEPENDS_${PN} = ""
RDEPENDS_${PN}_emulator = "dhcp-client"

FILES_${PN} += "${base_libdir}"
