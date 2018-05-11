# Copyright (c) 2017-2018 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos2"

# Add gallium, gallium-llvmpipe, opengl and enable wayland even without wayland in DISTRO_FEATURES
PACKAGECONFIG_qemuall = "opengl gbm egl gles dri wayland gallium gallium-llvm"

# Add virgl gallium driver
GALLIUMDRIVERS_qemuall = "virgl"
GALLIUMDRIVERS_LLVM_qemuall = "virgl"
DRIDRIVERS_qemuall = "swrast"

# Enable wayland even without wayland in DISTRO_FEATURES
PLATFORMS_qemuall = "drm,wayland"
