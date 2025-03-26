# Copyright (c) 2024-2025 LG Electronics, Inc.

SUMMARY = "Media Recorder Service"
AUTHOR = "Sungho Lee <shl.lee@lge.com>"
SECTION = "webos/services"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://LICENSE;md5=89aea4e17d99a7cacdbeed46a0096b10 \
    file://oss-pkg-info.yaml;md5=2bdfe040dcf81b4038370ae96036c519 \
"

DEPENDS = "glib-2.0 luna-service2 pmloglib nlohmann-json"

# Record pipeline
DEPENDS += "boost gstreamer1.0 gstreamer1.0-plugins-base gstreamer1.0-plugins-bad"

WEBOS_VERSION = "1.0.0-17_255f198c5ee9a9ca5aec4445cd34d5c20086df9f"
INC_PR = "r6"

inherit webos_component
inherit webos_cmake
inherit webos_enhanced_submissions
inherit webos_public_repo
inherit webos_system_bus
inherit webos_daemon
inherit features_check
ANY_OF_DISTRO_FEATURES = "vulkan opengl"

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

# Define machine
PACKAGECONFIG[use-rpi] = "-DUSE_RPI:BOOL=True,-DUSE_RPI:BOOL=False,"

# Build a native app for testing the media recorder
PACKAGECONFIG[test-apps] = "-DWITH_RECORD_TEST=ON,-DWITH_RECORD_TEST=OFF, webos-wayland-extensions jpeg, ${PN}-test-apps"

# Calculate display plane resource
PACKAGECONFIG[use-display-resource] = "-DUSE_DISPLAY_RESOURCE:BOOL=True,-DUSE_DISPLAY_RESOURCE:BOOL=False,"

# Pro UMS
PACKAGECONFIG[pro-ums] = "-DPRO_UMS:BOOL=True,-DPRO_UMS:BOOL=False,"

PACKAGECONFIG_USE_DISPLAY_RESOURCE ?= "use-display-resource"
PACKAGECONFIG:append = " ${PACKAGECONFIG_USE_DISPLAY_RESOURCE}"

# for test-apps
PACKAGES += "${PN}-test-apps"
RDEPENDS:${PN}-test-apps = "${PN}"
FILES:${PN}-test-apps = "${webos_applicationsdir}"

# build media recorder
PACKAGECONFIG[build-media-recorder] = "-DMEDIA_RECORDER_BUILD:BOOL=True,-DMEDIA_RECORDER_BUILD=False,"

# build buffer encoder
PACKAGECONFIG[build-buffer-encoder] = "-DBUFFER_ENCODER_BUILD:BOOL=True,-DBUFFER_ENCODER_BUILD=False,"

# Use gst v4l2 encoder
PACKAGECONFIG[gst-v4l2-encoder] = "-DGST_V4L2_ENCODER:BOOL=True,-DGST_V4L2_ENCODER:BOOL=False,"
