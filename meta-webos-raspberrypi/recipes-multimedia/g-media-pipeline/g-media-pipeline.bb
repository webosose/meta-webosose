# Copyright (c) 2018 LG Electronics, Inc.

SUMMARY = "G media pipeline is a media pipeline which uses GStreamer"
AUTHOR = "Jinwoo Ahn <jinwoo.ahn@lge.com>"
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

COMPATIBLE_MACHINE = "^rpi$"

DEPENDS = "boost userland gstreamer1.0 gstreamer1.0-plugins-base gstreamer1.0-plugins-bad umediaserver media-resource-calculator"

WEBOS_VERSION = "1.0.0-8_4acdae36e684e3e09130ad30bde93241e74b3f3c"
PR = "r1"

SRC_URI="${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

FILES_SOLIBSDEV = ""
FILES_${PN} += "${libdir}/*.so"
