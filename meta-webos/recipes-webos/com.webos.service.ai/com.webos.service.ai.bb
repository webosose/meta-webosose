# Copyright (c) 2018-2023 LG Electronics, Inc.

SUMMARY = "Ai service for voice/face/gesture recognition"
AUTHOR = "Kyungjik Min <dp.min@lge.com>"
SECTION = "webos/extended-service"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57 \
    file://oss-pkg-info.yaml;md5=5e240f6c8ed6312f22c06e00a91dd340 \
"

DEPENDS = "glib-2.0 luna-service2 json-c pmloglib libgoogleassistant"

WEBOS_VERSION = "1.0.0-11_6bc7a16f334f58dfa4b439b6f849d79a1b72871b"
PR = "r8"

inherit systemd
inherit webos_public_repo
inherit webos_component
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_pkgconfig
inherit webos_system_bus
inherit webos_machine_impl_dep

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

inherit webos_systemd
WEBOS_SYSTEMD_SERVICE = "ai.service"

# The same restriction as in
# meta-webos/recipes-upstreamable/snowboy/snowboy_%.bbappend
# libgoogleassistant depends on snowboy
COMPATIBLE_MACHINE = "rpi|aarch64|x86-64|qemux86-64"

# All service files will be managed in meta-lg-webos.
# The service file in the repository is not used, so please delete it.
# See the page below for more details.
# http://collab.lge.com/main/pages/viewpage.action?pageId=2031668745
do_install:append() {
    rm ${D}${sysconfdir}/systemd/system/ai.service
}
