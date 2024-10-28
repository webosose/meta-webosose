# Copyright (c) 2021-2024 LG Electronics, Inc.

SUMMARY = "Media codec interface for webOS"
AUTHOR = "Pankaj Kumar Maharana <pankaj.maharana@lge.com>"
SECTION = "webos/media"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=89aea4e17d99a7cacdbeed46a0096b10 \
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
inherit features_check
ANY_OF_DISTRO_FEATURES = "vulkan opengl"

COMPATIBLE_MACHINE = "^qemux86$|^qemux86-64$|^raspberrypi3$|^raspberrypi3-64$|^raspberrypi4$|^raspberrypi4-64$"

DEPENDS = "boost gstreamer1.0 gstreamer1.0-plugins-base gstreamer1.0-plugins-bad umediaserver media-resource-calculator"
DEPENDS:append:rpi = " virtual/libomxil"

WEBOS_VERSION = "1.0.0-32_c1e4dd09d9294e8d79e4b94bdb15ba2bf7e23f05"
PR = "r17"

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"

S = "${WORKDIR}/git"

PACKAGECONFIG_REACQUIRE ?= "support-reacquire"
PACKAGECONFIG ?= "${PACKAGECONFIG_REACQUIRE}"
PACKAGECONFIG[support-reacquire] = "-DSUPPORT_REACQUIRE:BOOL=True,-DSUPPORT_REACQUIRE:BOOL=False"
