# Copyright (c) 2018-2024 LG Electronics, Inc.

FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

EXTENDPRAUTO:append = "webos23"
SYSTEMD_SERVICE:${PN}:remove = "connman.service"
# connman.service is provided by connman-conf
RDEPENDS:${PN} += "connman-conf"

WEBOS_VERSION = "1.41-11_829ccdc4b0ced34982c1cdc7dec06d8b4c26cac3"
WEBOS_REPO_NAME = "connman-webos"
SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

inherit webos_component
inherit webos_public_repo
inherit webos_enhanced_submissions

do_install:append() {
    rm -vf ${D}${systemd_unitdir}/system/connman.service
}
