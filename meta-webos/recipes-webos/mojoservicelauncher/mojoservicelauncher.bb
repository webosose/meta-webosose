# Copyright (c) 2012-2025 LG Electronics, Inc.

SUMMARY = "webOS component responsible for launching the node.js services"
AUTHOR = "Rajesh Gopu I.V <rajeshgopu.iv@lge.com>"
SECTION = "webos/frameworks"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57 \
    file://oss-pkg-info.yaml;md5=bc052cc0f232b815569ebc81a4ef6490 \
"

DEPENDS = "boost libpbnjson"
RDEPENDS:${PN} = "nodejs"
# fork_server.js wants to load these:
RDEPENDS:${PN} += "nodejs-module-webos-dynaload nodejs-module-webos-pmlog nodejs-module-webos-sysbus mojoloader"

# The same restrition as nodejs
COMPATIBLE_MACHINE:armv4 = "(!.*armv4).*"
COMPATIBLE_MACHINE:armv5 = "(!.*armv5).*"
COMPATIBLE_MACHINE:mips64 = "(!.*mips64).*"

WEBOS_VERSION = "3.0.2-8_16771a22ae39002f867e60bb0726c66c0f5a532c"

PR = "r11"

inherit webos_component
inherit webos_public_repo
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_daemon
inherit webos_distro_variant_dep

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

PACKAGECONFIG:append = " ${@bb.utils.filter('DISTRO_FEATURES', 'smack', d)}"
PACKAGECONFIG[smack] = "-Denable_webos_smack=true:BOOL=TRUE"

FILES:${PN} += "${webos_prefix}/nodejs ${webos_servicesdir} ${webos_frameworksdir}"
