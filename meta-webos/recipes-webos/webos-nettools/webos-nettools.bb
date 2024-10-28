# Copyright (c) 2021-2024 LG Electronics, Inc.

DESCRIPTION = "Luna-service2 service providing network utility tools like ping and arping"
AUTHOR = "Muralidhar N <muralidhar.n@lge.com>"
SECTION = "webos/services"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://LICENSE;md5=2763f3ed850f8412903ea776e0526bea \
    file://oss-pkg-info.yaml;md5=19d9ec0fe1295511ff6de5bf74c43d46 \
"

DEPENDS = "luna-service2 libpbnjson glib-2.0"
RDEPENDS:${PN} = "iputils"

WEBOS_REPO_NAME = "com.webos.service.nettools"

WEBOS_VERSION = "1.1.0-9_6ef8de89551bf4ee5d54ff7ab24f3d409a22726c"
PR = "r3"

inherit webos_component
inherit webos_public_repo
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_daemon
inherit webos_system_bus

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE} \
    file://nettools_access_control.conf \
"
S = "${WORKDIR}/git"

do_install:append() {
    install -d ${D}${sysconfdir}
    install -m 444 ${UNPACKDIR}/nettools_access_control.conf ${D}${sysconfdir}/nettools_access_control.conf
}

FILES:${PN} += "${sysconfdir}/nettools_access_control.conf"

# Ensure the configuration file is included in the package
CONFFILES:${PN} += "${sysconfdir}/nettools_access_control.conf"
