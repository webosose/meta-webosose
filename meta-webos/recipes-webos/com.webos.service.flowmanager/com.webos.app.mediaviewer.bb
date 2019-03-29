# Copyright (c) 2019 LG Electronics, Inc.

SUMMARY = "webOS OSE sample app for flow"
AUTHOR  = "Namsu Kim  <namsu81.kim@lge.com>"
LICENSE = "CLOSED"

inherit webos_app

require flowmanager.inc

WEBOS_REPO_NAME = "com.webos.service.flowmanager"
S = "${WORKDIR}/git/apps"

do_install() {
    install -d ${D}${webos_applicationsdir}/${PN}
    cp -rv ${S}/${PN}/* ${D}${webos_applicationsdir}/${PN}
}

FILES_${PN} += "${webos_applicationsdir}"

