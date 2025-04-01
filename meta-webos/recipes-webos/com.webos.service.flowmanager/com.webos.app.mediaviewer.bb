# Copyright (c) 2019-2025 LG Electronics, Inc.

SUMMARY = "webOS OSE sample app for flow"
AUTHOR = "Sangwoo Kang <sangwoo82.kang@lge.com>"
SECTION = "webos/apps"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://../LICENSE;md5=89aea4e17d99a7cacdbeed46a0096b10 \
    file://../oss-pkg-info.yaml;md5=66c8021042d90a78e662c2cf62e2ee13 \
"

inherit webos_app
inherit webos_enhanced_submissions

require flowmanager.inc
PR = "r2"

WEBOS_REPO_NAME = "com.webos.service.flowmanager"
S = "${WORKDIR}/git/apps"

do_install() {
    install -d ${D}${webos_applicationsdir}/${PN}
    cp -rv ${S}/${BPN}/* ${D}${webos_applicationsdir}/${PN}
}

FILES:${PN} += "${webos_applicationsdir}"

