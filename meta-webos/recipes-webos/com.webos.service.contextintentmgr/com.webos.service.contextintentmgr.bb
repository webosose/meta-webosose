# Copyright (c) 2018-2019 LG Electronics, Inc.

SUMMARY = "Node-red based context intent manager (CIM)"
AUTHOR = "Tirthadeep Roy <tirthadeep.roy@lge.com>"
SECTION = "webos/extended-service"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

DEPENDS = "nodejs-module-node-red"

COMPATIBLE_MACHINE = "^raspberrypi3$"
COMPATIBLE_MACHINE_raspberrypi3-64 = "^$"

WEBOS_VERSION = "1.0.0-6_cfa4da54851127f44fe2a343716085d5245fda21"
PR = "r1"

# The same restrition as nodejs (and nodejs-module-node-red)
COMPATIBLE_MACHINE_armv4 = "(!.*armv4).*"
COMPATIBLE_MACHINE_armv5 = "(!.*armv5).*"
COMPATIBLE_MACHINE_mips64 = "(!.*mips64).*"

inherit systemd
inherit webos_public_repo
inherit webos_component
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_system_bus
inherit webos_machine_impl_dep

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

do_install_append() {
    install -d ${D}${sysconfdir}/systemd/system/scripts
    install -v -m 744 ${S}/files/systemd/scripts/contextintentmgr.sh ${D}${sysconfdir}/systemd/system/scripts/
}

FILES_${PN} += "${webos_servicesdir} ${webos_sysconfdir}"
SYSTEMD_SERVICE_${PN} = "contextintentmgr.service"
