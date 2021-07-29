# Copyright (c) 2018-2020 LG Electronics, Inc.

EXTENDPRAUTO:append:rpi = "webosrpi6"

CPPFLAGS:append:rpi = " -I${STAGING_INCDIR}/IL \
    -I${STAGING_INCDIR}/interface/vcos/pthreads \
    -I${STAGING_INCDIR}/interface/vmcs_host/linux"

DEPENDS:append:rpi = " virtual/egl virtual/libomxil"

EXTRA_LDFLAGS:rpi = "-lEGL -lbcm_host -lvcos -lvchiq:arm -lopenmaxil"

# raspberrypi3-64 version of userland doesn't provide bcm_host and openmaxil libraries
# (that's actually the only difference between set of files staged by userland build for raspberrypi3 and raspberrypi3-64)
EXTRA_LDFLAGS:raspberrypi3-64 = "-lEGL -lvcos -lvchiq:arm"

# Build for raspberrypi4
EXTRA_LDFLAGS:raspberrypi4-64 = "-lEGL -lvcos -lvchiq:arm"

LDFLAGS:append:rpi = " ${EXTRA_LDFLAGS}"

GSTREAMER_1_0_OMX_CORE_NAME:rpi = "${libdir}/libopenmaxil.so"
GSTREAMER_1_0_OMX_TARGET:rpi = "rpi"

FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

SRC_URI:append:rpi = " \
    file://0001-fix-adaptive-resolution-change-in-seek-issue.patch \
    file://0002-fix-brcmegl-dependencies-from-configure.patch \
    file://0003-decrease-ranking-of-omxh264dec.patch \
"

