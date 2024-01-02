# Copyright (c) 2021-2024 LG Electronics, Inc.

SUMMARY = "Simple webserver container with docker-compose"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

inherit systemd webos_filesystem_paths

PV = "0.1.1"
PR = "r2"

SRC_URI = "file://docker-simple-webserver_${PV}.tar.bz2"
SRC_URI[md5sum] = "77751dcfeb4ac270d94c9ff4ff02f2aa"
SRC_URI[sha256sum] = "c0d05406d9e5a7e7b3fcc76b0a3a2808b3a9a35ba49b273b5ba3ed81ecb42a6c"

S = "${WORKDIR}/docker-simple-webserver"

do_install() {
    # install systemd service files
    install -d ${D}${sysconfdir}/systemd/system
    install -d ${D}${sysconfdir}/systemd/system/docker.service.d
    install -d ${D}${sysconfdir}/systemd/system/scripts
    install -m 644 ${S}/docker-simple-webserver.service ${D}${sysconfdir}/systemd/system
    install -m 644 ${S}/docker.service.d/override.conf ${D}${sysconfdir}/systemd/system/docker.service.d
    install -m 755 ${S}/allow_docker_dev_server.sh ${D}${sysconfdir}/systemd/system/scripts

    # don't start service automatically
    sed -i '/\[Install\]/,+2d' ${D}${sysconfdir}/systemd/system/docker-simple-webserver.service

    # copy docker-compose file and related files
    install -d ${D}${datadir}
    cp -rv ${S}/docker-simple-webserver ${D}${datadir}
}

FILES:${PN} += "${webos_mountablestoragedir}"
SYSTEMD_SERVICE:${PN} = "docker-simple-webserver.service"
