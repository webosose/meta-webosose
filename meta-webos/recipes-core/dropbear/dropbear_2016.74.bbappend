# Copyright (c) 2013-2018 LG Electronics, Inc.

inherit webos_machine_impl_dep

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

EXTENDPRAUTO_append = "webos6"

# set the sftp path
# this overrides default value set in oe-core's dropbear.inc
CFLAGS_append_emulator = " -DSFTPSERVER_PATH=\\"/usr/libexec/sftp-server\\""

# move startup scripts in different packages
PACKAGES =+ "${PN}-sysvinit"
FILES_${PN}-sysvinit = "${sysconfdir}/init.d"

# for update-rc.bbclass to know where to put postinst/prerm
UPDATERCPN = "${PN}-sysvinit"

SRC_URI += "file://dropbear.conf"

# The use of Upstart 1.8 by products requires that we replace do_install().
do_install_append() {
    install -d ${D}${sysconfdir}/init
    install -m 0644 ${WORKDIR}/dropbear.conf ${D}${sysconfdir}/init
}

do_install_append_emulator() {
    # Start dropbear (and dhclient) as early as possible
    # in emulator to make boot-time debugging easier.
    sed -i 's/^start on rest-boot-done$/start on started dhclient/g' ${D}${sysconfdir}/init/dropbear.conf
}

# Remove runtime dependency on separate dropbear-upstart package with Upstart 0.3 job file.
RDEPENDS_${PN} = "openssh-sftp-server"
RDEPENDS_${PN}_emulator = "dhcp-client"
