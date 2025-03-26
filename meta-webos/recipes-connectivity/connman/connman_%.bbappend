# Copyright (c) 2018-2025 LG Electronics, Inc.

FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

EXTENDPRAUTO:append = "webos26"
SYSTEMD_SERVICE:${PN}:remove = "connman.service"
# connman.service is provided by connman-conf
RDEPENDS:${PN} += "connman-conf"

WEBOS_VERSION = "1.41-13_cd93100dc29769c1b3640aa9e5ab9306a13bf477"
WEBOS_REPO_NAME = "connman-webos"
SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

inherit webos_component
inherit webos_public_repo
inherit webos_enhanced_submissions

do_install:append() {
    rm -vf ${D}${systemd_system_unitdir}/connman.service
}
