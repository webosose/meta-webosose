# Copyright (c) 2018-2019 LG Electronics, Inc.

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

# media-resource-calculator since submissions 47 isn't usable for any other MACHINE than
# raspberrypi3
# raspberrypi3-64
# qemux86
COMPATIBLE_MACHINE = "^qemux86$|^raspberrypi3$|^raspberrypi3-64$"

DEPENDS = "boost gstreamer1.0 gstreamer1.0-plugins-base gstreamer1.0-plugins-bad umediaserver media-resource-calculator"
DEPENDS_append_rpi = " virtual/libomxil"

WEBOS_VERSION = "1.0.0-20_5c238aa6c034a982c10038d51265119f1354c994"
PR = "r4"

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

# Build for raspberrypi4
COMPATIBLE_MACHINE_append = "|^raspberrypi4$|^raspberrypi4-64$"
# From http://gpro.lge.com/251820
SRC_URI += "file://0001-Add-raspberrypi4-and-raspberrypi4-64-targets.patch"

FILES_SOLIBSDEV = ""
FILES_${PN} += "${libdir}/*.so"
