# Copyright (c) 2013-2019 LG Electronics, Inc.

inherit webos_machine_impl_dep

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

EXTENDPRAUTO:append = "webos7"

# (In the emulator) our openssh is installed in /opt prefix, set the sftp path
# this overrides default value set in oe-core's dropbear.inc
CFLAGS:append:emulator = " -DSFTPSERVER_PATH=\\"/opt/openssh/lib/openssh/sftp-server\\""

# move startup scripts in different packages
PACKAGES =+ "${PN}-sysvinit"
FILES:${PN}-sysvinit = "${sysconfdir}/init.d"

# for update-rc.bbclass to know where to put postinst/prerm
UPDATERCPN = "${PN}-sysvinit"

SRC_URI += "file://dropbear.service"

do_install:append() {
    install -d ${D}${sysconfdir}/systemd/system
    install -m 0644 ${WORKDIR}/dropbear.service ${D}${sysconfdir}/systemd/system/
    ln -sf /dev/null ${D}/${sysconfdir}/systemd/system/dropbear.socket
}

# Remove runtime dependency on separate dropbear-upstart package with Upstart 0.3 job file.
RDEPENDS:${PN} = ""
RDEPENDS:${PN}:emulator = "dhcp-client"

FILES:${PN} += "${base_libdir}"
