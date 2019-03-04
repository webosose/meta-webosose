# Copyright (c) 2019 LG Electronics, Inc.

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


WEBOS_VERSION = "1.0.0-3_e090fdcce891395c83e6c0aab848e4decbeb8639"
PR = "r0"

DEPENDS = "boost userland gstreamer1.0 gstreamer1.0-plugins-base gstreamer1.0-plugins-bad pkgconfig umediaserver media-resource-calculator com.webos.service.camera"

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git/"

COMPATIBLE_MACHINE = "^raspberrypi3$"
COMPATIBLE_MACHINE_raspberrypi3-64 = "^$"

FILES_SOLIBSDEV = ""
FILES_${PN} += "${libdir}/*.so"
FILES_${PN} += "${libdir}/gstreamer-1.0/*.so"
