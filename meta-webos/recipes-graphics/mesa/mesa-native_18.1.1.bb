SUMMARY = "A free implementation of the OpenGL API"
DESCRIPTION = "Mesa is an open-source implementation of the OpenGL specification - \
a system for rendering interactive 3D graphics.  \
A variety of device drivers allows Mesa to be used in many different environments \
ranging from software emulation to complete hardware acceleration for modern GPUs. \
Mesa is used as part of the overall Direct Rendering Infrastructure and X.org \
environment."

HOMEPAGE = "http://mesa3d.org"
BUGTRACKER = "https://bugs.freedesktop.org"
SECTION = "x11"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://docs/license.html;md5=725f991a1cc322aa7a0cd3a2016621c4"

SRC_URI = "https://mesa.freedesktop.org/archive/mesa-${PV}.tar.xz"
SRC_URI[md5sum] = "063468c930ff61d211ece0191874fa95"
SRC_URI[sha256sum] = "d3312a2ede5aac14a47476b208b8e3a401367838330197c4588ab8ad420d7781"

PROVIDES = "virtual/egl-native virtual/libgl-native"

DEPENDS = "expat-native makedepend-native flex-native bison-native libxml2-native zlib-native chrpath-replacement-native libdrm-native"
EXTRANATIVEPATH += "chrpath-native"

inherit autotools pkgconfig gettext native

DRIDRIVERS = "swrast,radeon,r200,nouveau,i965,i915"
# drop r300, swr and radeonsi, because they need llvm
# configure: error: --enable-llvm is required when building r300, radeonsi
# freedreno needs libdrm_freedreno
# configure: error: Package requirements (libdrm >= 2.4.74 libdrm_freedreno >= 2.4.74) were not met:
# vc4 needs libdrm_vc4
# configure: error: Package requirements (libdrm >= 2.4.69 libdrm_vc4 >= 2.4.69) were not met:
GALLIUM_DRIDRIVERS = "i915,nouveau,r600,svga,swrast,virgl,etnaviv,imx"

EXTRA_OECONF = "--with-platforms=drm,x11 --disable-dri3 --with-dri-drivers=${DRIDRIVERS} --with-gallium-drivers=${GALLIUM_DRIDRIVERS}"

PACKAGECONFIG ??= "dri gbm glx"
PACKAGECONFIG[gbm] = "--enable-gbm,--disable-gbm"
PACKAGECONFIG[glx] = "--enable-glx,--disable-glx, xorgproto libxdamage libxfixes xext"
PACKAGECONFIG[dri] = "--enable-dri,--disable-dri, xorgproto libdrm"
# libdrm_intel"
PACKAGECONFIG[dri3] = "--enable-dri3, --disable-dri3, xorgproto libxshmfence"

# because we cannot rely on the fact that all apps will use pkgconfig,
# make eglplatform.h independent of MESA_EGL_NO_X11_HEADER
# e.g. virglrenderer-native fails without this
do_install_append() {
    sed -i -e 's/^#if defined(MESA_EGL_NO_X11_HEADERS)$/#if 1 \/\/ defined(MESA_EGL_NO_X11_HEADERS)/g' ${D}${includedir}/EGL/eglplatform.h
}
