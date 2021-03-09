# Copyright (c) 2021 LG Electronics, Inc.

DESCRIPTION = "Luna-service2 service providing network utility tools like ping and arping"
AUTHOR = "Rakes Pani <rakes.pani@lge.com>"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10 \
                    file://oss-pkg-info.yaml;md5=19d9ec0fe1295511ff6de5bf74c43d46 \
"

SECTION = "webos/services"

DEPENDS = "luna-service2 libpbnjson glib-2.0"
RDEPENDS_${PN} = "iputils"

WEBOS_REPO_NAME = "com.webos.service.nettools"

WEBOS_VERSION = "1.1.0-5_bb5fc25236a69ffc7f8eb525b69ba264a64daba0"
PR = "r0"

inherit webos_component
inherit webos_public_repo
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_daemon
inherit webos_system_bus


SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"
