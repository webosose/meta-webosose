# Copyright (c) 2019-2020 LG Electronics, Inc.

SUMMARY = "g-camera-pipeline is a player which uses GStreamer"
AUTHOR = "Praveen P <praveen.p@lge.com>"
SECTION = "webos/media"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

inherit webos_component
inherit webos_cmake
inherit webos_system_bus
inherit webos_enhanced_submissions
inherit webos_public_repo
inherit webos_machine_impl_dep
inherit webos_machine_dep
inherit webos_pkgconfig

WEBOS_VERSION = "1.0.0-13_f09fb1b2bd1ea40235d9dc6b66c94c9c85b45f08"
PR = "r3"

DEPENDS = "boost gstreamer1.0 gstreamer1.0-plugins-base gstreamer1.0-plugins-bad pkgconfig umediaserver media-resource-calculator com.webos.service.camera"
DEPENDS_append_rpi = " userland"

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git/"

# See the restrictions in CMakeLists.txt
COMPATIBLE_MACHINE = "^raspberrypi3$"

# From http://gpro.lge.com/251819
# But needs to be adapted to GAV, currently it fails with:
# g-camera-pipeline/1.0.0-13-r3/git/src/resourcefacilitator/requestor.cpp:25:10: fatal error: MDCClient.h: No such file or directory
# because raspberrypi4 version of umediaserver doesn't provide MDCClient.h
SRC_URI += "file://0001-Add-raspberrypi4-and-raspberrypi4-64-targets.patch"

FILES_SOLIBSDEV = ""
FILES_${PN} += "${libdir}/*.so"
FILES_${PN} += "${libdir}/gstreamer-1.0/*.so"

# From http://gpro.lge.com/250944
SRC_URI += "file://0001-Fix-return-statement.patch"
