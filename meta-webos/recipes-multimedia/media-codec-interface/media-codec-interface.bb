# Copyright (c) 2021 LG Electronics, Inc.

SUMMARY = "Media codec interface for webOS"
AUTHOR = "Pankaj Maharana <pankaj.maharana@lge.com>"
SECTION = "webos/media"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"
LIC_FILES_CHKSUM += "file://oss-pkg-info.yaml;md5=2bdfe040dcf81b4038370ae96036c519"

inherit webos_component
inherit webos_cmake
inherit webos_system_bus
inherit webos_enhanced_submissions
inherit webos_public_repo
inherit webos_machine_impl_dep
inherit webos_machine_dep
inherit webos_pkgconfig

COMPATIBLE_MACHINE = "^qemux86$|^qemux86-64$|^raspberrypi3$|^raspberrypi3-64$|^raspberrypi4$|^raspberrypi4-64$"

DEPENDS = "boost gstreamer1.0 gstreamer1.0-plugins-base gstreamer1.0-plugins-bad umediaserver media-resource-calculator"
DEPENDS:append:rpi = " virtual/libomxil"

WEBOS_VERSION = "1.0.0-7_e987aaf361d9e6d94c0b446c6e3bee544c5fdf47"
PR = "r5"

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE};name=main"

USE_ENCODER ?= "GST"
EXTRA_OECMAKE += "-DUSE_ENCODER_BUILD:STRING=${USE_ENCODER}"

S = "${WORKDIR}/git"

FILES_SOLIBSDEV = ""
FILES:${PN} += "${libdir}/*.so"
