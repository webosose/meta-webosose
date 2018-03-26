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
SRC_URI[md5sum] = "e40bb428a263bd28cbf6478dae45b207"
SRC_URI[sha256sum] = "69f472a874b1122404fa0bd13e2d6bf87eb3b9ad9c21d2f39872a96d83d9e5f5"

PROVIDES = "virtual/egl-native virtual/libgl-native"

DEPENDS = "expat-native makedepend-native flex-native bison-native libxml2-native zlib-native chrpath-replacement-native libdrm-native"
EXTRANATIVEPATH += "chrpath-native"

inherit autotools pkgconfig gettext native


DRIDRIVERS_append_x86-64 = ",radeon,r200,nouveau,i965,i915"

EXTRA_OECONF = "--with-platforms=drm,x11 --disable-dri3 --with-dri-drivers=${DRIDRIVERS} --with-gallium-drivers=virgl"

PACKAGECONFIG ??= "dri gbm glx"
PACKAGECONFIG[gbm] = "--enable-gbm,--disable-gbm"
PACKAGECONFIG[glx] = "--enable-glx,--disable-glx,glproto dri2proto libxdamage libxfixes"
PACKAGECONFIG[dri] = "--enable-dri,--disable-dri, dri2proto libdrm"
# libdrm_intel"
PACKAGECONFIG[dri3] = "--enable-dri3, --disable-dri3, dri3proto presentproto libxshmfence"

# because we cannot rely on the fact that all apps will use pkgconfig,
# make eglplatform.h independent of MESA_EGL_NO_X11_HEADER
# e.g. virglrenderer-native fails without this
do_install_append() {
    sed -i -e 's/^#if defined(MESA_EGL_NO_X11_HEADERS)$/#if 1 \/\/ defined(MESA_EGL_NO_X11_HEADERS)/g' ${D}${includedir}/EGL/eglplatform.h
}
