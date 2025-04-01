# Copyright (c) 2023-2025 LG Electronics, Inc.

SUMMARY = "A service to set-up partitions on SD"
AUTHOR = "JeongBong Seo <jb.seo@lge.com>"
SECTION = "webos/base"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

RPROVIDES:${PN} = "part-initializer"

RDEPENDS:${PN} = "e2fsprogs-resize2fs parted"

PR = "r6"

inherit systemd
inherit webos_filesystem_paths

SRC_URI = " \
    file://setup-partitions.service \
    file://setup-partitions.sh \
"

do_install() {
    # install service/script files
    install -d ${D}${systemd_system_unitdir}
    install -v -m 0644 ${UNPACKDIR}/setup-partitions.service ${D}${systemd_system_unitdir}
    install -d ${D}${sysconfdir}/systemd/system/scripts
    install -v -m 0755 ${UNPACKDIR}/setup-partitions.sh ${D}${sysconfdir}/systemd/system/scripts

    # enable overlay by default
    install -d ${D}${webos_sysmgr_localstatedir}/preferences
    touch ${D}${webos_sysmgr_localstatedir}/preferences/mount_overlay_enabled
}

SYSTEMD_SERVICE:${PN} = " setup-partitions.service"
