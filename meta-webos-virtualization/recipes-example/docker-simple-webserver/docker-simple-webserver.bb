# Copyright (c) 2021-2022 LG Electronics, Inc.

SUMMARY = "Simple webserver container with docker-compose"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

inherit systemd webos_filesystem_paths

PV = "0.1.0"
PR = "r1"

SRC_URI = "file://docker-simple-webserver_${PV}.tar.bz2"
SRC_URI[md5sum] = "7b24cf1e091a13933b82effa635d418a"
SRC_URI[sha256sum] = "274032c6a3c854cc3d9412ec73b05ffc28527e31b57e4fed685f2c36f57974d5"

S = "${WORKDIR}/docker-simple-webserver"

do_install() {
    # install systemd service file
    install -d ${D}${sysconfdir}/systemd/system
    install -m 644 ${S}/docker-simple-webserver.service ${D}${sysconfdir}/systemd/system

    # don't start service automatically
    sed -i '/\[Install\]/,+2d' ${D}${sysconfdir}/systemd/system/docker-simple-webserver.service

    # install docker daemon file to access webosdev.lge.com regisgry
    install -d ${D}${webos_mountablestoragedir}/docker
    install -m 644 ${S}/daemon.json ${D}${webos_mountablestoragedir}/docker

    # copy docker-compose file and related files
    install -d ${D}${datadir}
    cp -rv ${S}/docker-simple-webserver ${D}${datadir}
}

FILES:${PN} += "${webos_mountablestoragedir}"
SYSTEMD_SERVICE:${PN} = "docker-simple-webserver.service"
