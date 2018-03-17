# Copyright (c) 2017 LG Electronics, Inc.

EXTENDPRAUTO_append = "webosrpi3"

PROVIDES_remove = "\
    virtual/libgles2 \
    virtual/egl \
"

RPROVIDES_${PN}_remove ="\
    libgles2 \
    libgl \
    egl \
    libegl \
"

SRC_URI_remove = "\
    file://0001-Allow-applications-to-set-next-resource-handle.patch \
    file://0002-wayland-Add-support-for-the-Wayland-winsys.patch \
    file://0003-wayland-Add-Wayland-example.patch \
    file://0004-wayland-egl-Add-bcm_host-to-dependencies.patch \
    file://0005-interface-remove-faulty-assert-to-make-weston-happy-.patch \
    file://0006-zero-out-wl-buffers-in-egl_surface_free.patch \
    file://0007-initialize-front-back-wayland-buffers.patch \
    file://0008-Remove-RPC_FLUSH.patch \
    file://0009-fix-cmake-dependency-race.patch \
    file://0010-Fix-for-framerate-with-nested-composition.patch \
    file://0011-build-shared-library-for-vchostif.patch \
    file://0012-implement-buffer-wrapping-interface-for-dispmanx.patch \
    file://0013-Implement-triple-buffering-for-wayland.patch \
    file://0016-Allow-multiple-wayland-compositor-state-data-per-pro.patch \
"

# Remaining patches from meta-raspberrypi:
# file://0014-GLES2-gl2ext.h-Define-GL_R8_EXT-and-GL_RG8_EXT.patch
# file://0015-EGL-glplatform.h-define-EGL_CAST.patch

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"
SRC_URI_append = " file://0001-Remove-EGL-dependency.patch"

VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS_${PN}_append_class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS_${PN}_remove = "bash"

PROVIDES = "virtual/libomxil"
