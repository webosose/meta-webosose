# Copyright (c) 2022 LG Electronics, Inc.

SUMMARY = "SDK Agent service for telegraf"
AUTHOR = "Wonsang Ryu <wonsang.ryu@lge.com>"
SECTION = "webos/extended-service"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57 \
    file://oss-pkg-info.yaml;md5=7a1ae36458ee4a0a5f0d6c75b326c77e \
"

DEPENDS = "glib-2.0 luna-service2 json-c pmloglib libpbnjson"
RDEPENDS:${PN} += "telegraf"

WEBOS_VERSION = "1.0.0-1_63ea36940aaa2991898ffd6d70cdbffe635abab8"
PR = "r0"

inherit systemd
inherit webos_public_repo
inherit webos_component
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_pkgconfig
inherit webos_system_bus

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

SYSTEMD_SERVICE:${PN} = "com.webos.service.sdkagent.service"
