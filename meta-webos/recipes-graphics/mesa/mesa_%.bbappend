# Copyright (c) 2017-2019 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos4"

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

SRC_URI += " \
    file://0001-mesa-do-not-leak-ctx-Shader.ReferencedProgram-refere.patch \
    file://0001-virgl-Fix-flush-in-virgl_encoder_inline_write.patch \
"

# Add gallium, gallium-llvmpipe, opengl and enable wayland even without wayland in DISTRO_FEATURES
PACKAGECONFIG_qemuall = "opengl gbm egl gles dri wayland gallium gallium-llvm"

# Add virgl gallium driver
GALLIUMDRIVERS_qemuall = "virgl,svga"
GALLIUMDRIVERS_LLVM_qemuall = "virgl,svga"
DRIDRIVERS_qemuall = "swrast"

# Enable wayland even without wayland in DISTRO_FEATURES
PLATFORMS_qemuall = "drm,wayland"
