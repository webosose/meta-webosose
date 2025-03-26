# Copyright (c) 2019-2025 LG Electronics, Inc.

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
inherit features_check
ANY_OF_DISTRO_FEATURES = "vulkan opengl"

PR = "r20"

DEPENDS = "boost gstreamer1.0 gstreamer1.0-plugins-base gstreamer1.0-plugins-bad umediaserver media-resource-calculator com.webos.service.camera webos-wayland-extensions"
DEPENDS:append:rpi = " userland"

WEBOS_VERSION = "1.0.0-gav.74_34ab300c64090fc1fc88c04ef592aa782f90781d"

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"

S = "${WORKDIR}/git"

FILES_SOLIBSDEV = ""
FILES:${PN} += "${libdir}/*.so"
FILES:${PN} += "${libdir}/gstreamer-1.0/*.so"

# Define machine
PACKAGECONFIG[use-rpi] = "-DUSE_RPI:BOOL=True,-DUSE_RPI:BOOL=False,"
PACKAGECONFIG[use-emulator] = "-DUSE_EMULATOR:BOOL=True,-DUSE_EMULATOR:BOOL=False,"
PACKAGECONFIG:append:emulator = " use-emulator"

# Calculate display plane resource
PACKAGECONFIG[use-display-resource] = "-DUSE_DISPLAY_RESOURCE:BOOL=True,-DUSE_DISPLAY_RESOURCE:BOOL=False,"

# Use camsrc
PACKAGECONFIG[use-camsrc] = "-DUSE_CAMSRC:BOOL=True,-DUSE_CAMSRC:BOOL=False,"

# Pro UMS
PACKAGECONFIG[pro-ums] = "-DPRO_UMS:BOOL=True,-DPRO_UMS:BOOL=False,"

# for wayland compositer
PACKAGECONFIG[use-compositer-ver4] = "-DUSE_COMPOSITER_VER4:BOOL=True,-DUSE_COMPOSITER_VER4:BOOL=False,"

PACKAGECONFIG:webos = "use-display-resource use-camsrc use-compositer-ver4"
