# Copyright (c) 2021-2022 LG Electronics, Inc.

DESCRIPTION = "Luna-service2 service providing network utility tools like ping and arping"
AUTHOR = "Rakes Pani <rakes.pani@lge.com>"
SECTION = "webos/services"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://LICENSE;md5=2763f3ed850f8412903ea776e0526bea \
    file://oss-pkg-info.yaml;md5=19d9ec0fe1295511ff6de5bf74c43d46 \
"

DEPENDS = "luna-service2 libpbnjson glib-2.0"
RDEPENDS:${PN} = "iputils"

WEBOS_REPO_NAME = "com.webos.service.nettools"

WEBOS_VERSION = "1.1.0-6_5ebd0866e9709d88db9c433746ccfcbc7561d48f"
PR = "r1"

inherit webos_component
inherit webos_public_repo
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_daemon
inherit webos_system_bus

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"
