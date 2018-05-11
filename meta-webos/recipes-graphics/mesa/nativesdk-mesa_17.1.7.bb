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

PROVIDES = "virtual/nativesdk-egl"

DEPENDS = "nativesdk-zlib nativesdk-libdrm nativesdk-expat"

inherit autotools pkgconfig gettext nativesdk

DRIDRIVERS = "swrast,radeon,r200,nouveau,i965"
# drop r300, swr and radeonsi, because they need llvm
# configure: error: --enable-llvm is required when building r300, radeonsi
# freedreno needs libdrm_freedreno
# configure: error: Package requirements (libdrm >= 2.4.74 libdrm_freedreno >= 2.4.74) were not met:
# vc4 needs libdrm_vc4
# configure: error: Package requirements (libdrm >= 2.4.69 libdrm_vc4 >= 2.4.69) were not met:
# i915 needs libdrm_intel which isn't enabled in nativesdk-libdrm
# configure: error: Package requirements (libdrm >= 2.4.75 libdrm_intel >= 2.4.75) were not met:
GALLIUM_DRIDRIVERS = "nouveau,r600,svga,swrast,virgl,etnaviv,imx"

EXTRA_OECONF = "--with-platforms=drm --disable-glx --disable-dri3 --with-dri-drivers=${DRIDRIVERS} --with-gallium-drivers=${GALLIUM_DRIDRIVERS}"

PACKAGECONFIG ??= "gbm"
PACKAGECONFIG[gbm] = "--enable-gbm,--disable-gbm"

FILES_${PN} += "${libdir}/dri/"

# because we cannot rely on the fact that all apps will use pkgconfig,
# make eglplatform.h independent of MESA_EGL_NO_X11_HEADER
# e.g. virglrenderer-native fails without this
do_install_append() {
    sed -i -e 's/^#if defined(MESA_EGL_NO_X11_HEADERS)$/#if 1 \/\/ defined(MESA_EGL_NO_X11_HEADERS)/g' ${D}${includedir}/EGL/eglplatform.h
}
