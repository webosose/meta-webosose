# Copyright (c) 2019 LG Electronics, Inc.

SUMMARY = "webOS OSE sample app for flow"
AUTHOR  = "Namsu Kim  <namsu81.kim@lge.com>"
SECTION = "webos/apps"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://../LICENSE;md5=89aea4e17d99a7cacdbeed46a0096b10"

inherit webos_app
inherit webos_enhanced_submissions

require flowmanager.inc
PR = "r1"

WEBOS_REPO_NAME = "com.webos.service.flowmanager"
S = "${WORKDIR}/git/apps"

do_install() {
    install -d ${D}${webos_applicationsdir}/${PN}
    cp -rv ${S}/${BPN}/* ${D}${webos_applicationsdir}/${PN}
}

FILES_${PN} += "${webos_applicationsdir}"

