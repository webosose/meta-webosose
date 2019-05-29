# Copyright (c) 2018-2020 LG Electronics, Inc.

EXTENDPRAUTO_append_rpi = "webosrpi4"

# cannot enable rpi as GSTREAMER_1_0_OMX_TARGET, because since:
# https://github.com/GStreamer/gst-omx/commit/d7c589122b45d4f7735d561a028739d4e92f4805
# it depends on brcmegl which we explicitly delete from userland
# http://caprica.lgsvl.com:8080/Errors/Details/1476614
GSTREAMER_1_0_OMX_TARGET_rpi = "bellagio"

#EXTENDPRAUTO_append_rpi = "webosrpi3"

#CPPFLAGS_append_rpi = " -I${STAGING_INCDIR}/IL \
#    -I${STAGING_INCDIR}/interface/vcos/pthreads \
#    -I${STAGING_INCDIR}/interface/vmcs_host/linux"

#DEPENDS_append_rpi = " virtual/egl virtual/libomxil"

#EXTRA_LDFLAGS_raspberrypi3 = "-lEGL -lbcm_host -lvcos -lvchiq_arm -lopenmaxil"
# raspberrypi3-64 version of userland doesn't provide bcm_host and openmaxil libraries
# (that's actually the only difference between set of files staged by userland build for raspberrypi3 and raspberrypi3-64)
EXTRA_LDFLAGS_raspberrypi3-64 = "-lEGL -lvcos -lvchiq_arm"

# Build for raspberrypi4
EXTRA_LDFLAGS_raspberrypi4 = "-lEGL -lbcm_host -lvcos -lvchiq_arm -lopenmaxil"
EXTRA_LDFLAGS_raspberrypi4-64 = "-lEGL -lvcos -lvchiq_arm"

# LDFLAGS_append_rpi = " ${EXTRA_LDFLAGS}"

#GSTREAMER_1_0_OMX_CORE_NAME_rpi = "${libdir}/libopenmaxil.so"
#GSTREAMER_1_0_OMX_TARGET_rpi = "rpi"

#FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

#SRC_URI += "file://0001-fix-adaptive-resolution-change-in-seek-issue.patch"
