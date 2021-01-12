# Copyright (c) 2018-2021 LG Electronics, Inc.

SUMMARY = "G media pipeline is a media pipeline which uses GStreamer"
AUTHOR = "Jinwoo Ahn <jinwoo.ahn@lge.com>"
SECTION = "webos/media"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"
LIC_FILES_CHKSUM_raspberrypi4 += "file://oss-pkg-info.yaml;md5=2bdfe040dcf81b4038370ae96036c519"

inherit webos_component
inherit webos_cmake
inherit webos_system_bus
inherit webos_enhanced_submissions
inherit webos_public_repo
inherit webos_machine_impl_dep
inherit webos_machine_dep
inherit webos_pkgconfig

# media-resource-calculator since submissions 47 isn't usable for any other MACHINE than
# raspberrypi3
# raspberrypi3-64
# raspberrypi4
# raspberrypi4-64
# qemux86
COMPATIBLE_MACHINE = "^qemux86$|^raspberrypi3$|^raspberrypi3-64$|^raspberrypi4$|^raspberrypi4-64$"

DEPENDS = "boost gstreamer1.0 gstreamer1.0-plugins-base gstreamer1.0-plugins-bad umediaserver media-resource-calculator"
DEPENDS_append_rpi = " virtual/libomxil"

DEPENDS_append_raspberrypi4 = " webos-wayland-extensions"

WEBOS_VERSION = "1.0.0-21_ecd90bea31daca759dc40061749131f2a61b99c1"
PR = "r8"

WEBOS_GIT_PARAM_BRANCH_raspberrypi4 = "@gav"
WEBOS_VERSION_raspberrypi4 = "1.0.0-21.gav.18_c423469ef1f110d7d24d49d384333ab2ca349ffe"
SRC_URI_append_raspberrypi4 = " file://0001-LunaServiceClient.cpp-include-string-to-fix-build-wi.patch"

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

FILES_SOLIBSDEV = ""
FILES_${PN} += "${libdir}/*.so"
