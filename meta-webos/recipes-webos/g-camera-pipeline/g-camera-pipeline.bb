# Copyright (c) 2019-2024 LG Electronics, Inc.

SUMMARY = "g-camera-pipeline is a player which uses GStreamer"
AUTHOR = "Sungho Lee <shl.lee@lge.com>"
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
inherit webos_pkgconfig

PR = "r16"

DEPENDS = "boost gstreamer1.0 gstreamer1.0-plugins-base gstreamer1.0-plugins-bad umediaserver media-resource-calculator com.webos.service.camera webos-wayland-extensions"
DEPENDS:append:rpi = " userland"

WEBOS_VERSION = "1.0.0-gav.49_84c1ac89a789287f34a9b0f5ce0cd40c712365d7"

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"

S = "${WORKDIR}/git"

FILES_SOLIBSDEV = ""
FILES:${PN} += "${libdir}/*.so"
FILES:${PN} += "${libdir}/gstreamer-1.0/*.so"

# Define machine
PACKAGECONFIG[use-rpi] = "-DUSE_RPI:BOOL=True,-DUSE_RPI:BOOL=False,"
PACKAGECONFIG[use-emulator] = "-DUSE_EMULATOR:BOOL=True,-DUSE_EMULATOR:BOOL=False,"
PACKAGECONFIG:append:emulator = " use-emulator"
