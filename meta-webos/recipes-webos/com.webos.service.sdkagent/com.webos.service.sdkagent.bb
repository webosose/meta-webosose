# Copyright (c) 2022-2025 LG Electronics, Inc.

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

WEBOS_VERSION = "1.0.0-17_e575da31aca01c4e685eb5ac94c25a31fe1d6a04"
PR = "r1"

inherit systemd
inherit webos_public_repo
inherit webos_component
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_pkgconfig
inherit webos_system_bus

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

inherit webos_systemd
WEBOS_SYSTEMD_SERVICE = "com.webos.service.sdkagent.service"

# All service files will be managed in meta-lg-webos.
# The service file in the repository is not used, so please delete it.
# See the page below for more details.
# http://collab.lge.com/main/pages/viewpage.action?pageId=2031668745
do_install:append() {
    rm ${D}${sysconfdir}/systemd/system/com.webos.service.sdkagent.service
}
