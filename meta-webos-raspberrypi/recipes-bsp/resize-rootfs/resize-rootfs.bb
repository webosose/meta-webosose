# Copyright (c) 2019-2021 LG Electronics, Inc.

SUMMARY = "A service to expand rootfs to whole media"
AUTHOR = "JeongBong Seo <jb.seo@lge.com>"
SECTION = "webos/base"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

RDEPENDS_${PN} = "e2fsprogs-resize2fs parted"

PR = "r1"

inherit systemd

SRC_URI = " \
    file://resize-rootfs.service \
    file://resize-rootfs.sh \
"

do_install() {
    install -d ${D}${systemd_system_unitdir}
    install -v -m 0644 ${WORKDIR}/resize-rootfs.service ${D}${systemd_system_unitdir}
    install -d ${D}${sysconfdir}/systemd/system/scripts
    install -v -m 0755 ${WORKDIR}/resize-rootfs.sh ${D}${sysconfdir}/systemd/system/scripts
}

SYSTEMD_SERVICE_${PN} = " resize-rootfs.service"
