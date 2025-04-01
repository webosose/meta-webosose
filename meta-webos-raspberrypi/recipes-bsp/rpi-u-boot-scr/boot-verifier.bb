# Copyright (c) 2023-2025 LG Electronics, Inc.

SUMMARY = "Boot verifier service for updated u-boot env"
AUTHOR = "JeongBong Seo <jb.seo@lge.com>"
SECTION = "webos/base"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

RDEPENDS:${PN} = "u-boot-fw-utils u-boot-env"

PR = "r1"

inherit systemd

SRC_URI = " \
    file://boot-verifier.service \
"

do_install() {
    install -d ${D}${systemd_system_unitdir}
    install -v -m 0644 ${UNPACKDIR}/boot-verifier.service ${D}${systemd_system_unitdir}
}

SYSTEMD_SERVICE:${PN} = " boot-verifier.service"
