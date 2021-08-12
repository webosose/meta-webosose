# Copyright (c) 2019-2021 LG Electronics, Inc.

SUMMARY = "g-camera-pipeline is a player which uses GStreamer"
AUTHOR = "Praveen P <praveen.p@lge.com>"
SECTION = "webos/media"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
  file://LICENSE;md5=89aea4e17d99a7cacdbeed46a0096b10 \
  file://oss-pkg-info.yaml;md5=2bdfe040dcf81b4038370ae96036c519 \
"

inherit webos_component
inherit webos_cmake
inherit webos_system_bus
inherit webos_enhanced_submissions
inherit webos_public_repo
inherit webos_machine_impl_dep
inherit webos_machine_dep
inherit webos_pkgconfig

WEBOS_VERSION = "1.0.0-16_e7fc787fdb52abb4ebd5c18f798976f6ebffad9f"
PR = "r9"

WEBOS_GIT_PARAM_BRANCH_raspberrypi4 = "@gav"
WEBOS_VERSION_raspberrypi4 = "1.0.0-13.gav.20_20f5e22b8e1fae6c44a71b729415ee72d87a51e0"

DEPENDS = "boost gstreamer1.0 gstreamer1.0-plugins-base gstreamer1.0-plugins-bad pkgconfig umediaserver media-resource-calculator com.webos.service.camera"
DEPENDS_append_rpi = " userland"

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

# See the restrictions in CMakeLists.txt
COMPATIBLE_MACHINE = "^raspberrypi3$"
COMPATIBLE_MACHINE_append = "|^raspberrypi4$"

FILES_SOLIBSDEV = ""
FILES_${PN} += "${libdir}/*.so"
FILES_${PN} += "${libdir}/gstreamer-1.0/*.so"

DEPENDS_append = " webos-wayland-extensions"
