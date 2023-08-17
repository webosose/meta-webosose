# Copyright (c) 2018-2023 LG Electronics, Inc.

SUMMARY = "Node-red based context intent manager (CIM)"
AUTHOR = "Muthu Santhosh Kumar <santhosh.muthu@lge.com>"
SECTION = "webos/extended-service"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10 \
    file://oss-pkg-info.yaml;md5=fe9bd85c6f789c8ada9c8b72c7454254 \
"

DEPENDS = "nodejs-module-node-red"

WEBOS_VERSION = "1.0.0-17_af7499b3964c37bdf489819c10650ff1a8efd5ae"
PR = "r5"

# The same restrition as nodejs (and nodejs-module-node-red)
COMPATIBLE_MACHINE:armv4 = "(!.*armv4).*"
COMPATIBLE_MACHINE:armv5 = "(!.*armv5).*"
COMPATIBLE_MACHINE:mips64 = "(!.*mips64).*"

inherit systemd
inherit webos_public_repo
inherit webos_component
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_system_bus
inherit webos_machine_impl_dep

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

inherit webos_systemd
WEBOS_SYSTEMD_SERVICE = "contextintentmgr.service"
WEBOS_SYSTEMD_SCRIPT = "contextintentmgr.sh"

# All service files will be managed in meta-lg-webos.
# The service file in the repository is not used, so please delete it.
# See the page below for more details.
# http://collab.lge.com/main/pages/viewpage.action?pageId=2031668745
do_install:append() {
    rm -f ${D}${sysconfdir}/systemd/system/contextintentmgr.service
    rm -f ${D}${sysconfdir}/systemd/system/scripts/contextintentmgr.sh
}

FILES:${PN} += "${webos_servicesdir} ${webos_sysconfdir}"
