# Copyright (c) 2017 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos1"

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

SRC_URI_append_qemuall = " \
          file://0001-Preventing-a-zero-data-length-command-case.patch \
          "

# Add gallium, gallium-llvmpipe, opengl and enable wayland even without wayland in DISTRO_FEATURES
PACKAGECONFIG_qemuall = "opengl gbm egl gles dri wayland gallium"

# Add virgl gallium driver
GALLIUMDRIVERS_qemuall = "virgl"
GALLIUMDRIVERS_LLVM_qemuall = "virgl"
DRIDRIVERS_qemuall = "swrast"

# Enable wayland even without wayland in DISTRO_FEATURES
PLATFORMS_qemuall = "drm,wayland"
