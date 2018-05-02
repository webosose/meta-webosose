# Copyright (c) 2018-2019 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos2"

PACKAGECONFIG_remove = "rsvg"

# dispmanx moved from plugins-bad to plugins-base. but gstreamer upstream needs more work for this option.
# see https://github.com/agherzan/meta-raspberrypi/pull/250
# Remove both enable and disable options, because in our old Yocto 2.2 Morty there is still
# PACKAGECONFIG in meta-raspberrypi bbappend which will add one of them based on vc4graphics in MACHINE_FEATURES
EXTRA_OECONF_remove = "--enable-dispmanx"
EXTRA_OECONF_remove = "--disable-dispmanx"

# There is also undeclared dependency on lcms
# ERROR: QA Issue: gstreamer1.0-plugins-bad-colormanagement rdepends on lcms, but it isn't a build dependency, missing lcms in DEPENDS or PACKAGECONFIG? [build-deps]
# This problem doesn't exist with newer Yocto with RSS
DEPENDS += "lcms"

# Needed only for qemux86 build
FILESEXTRAPATHS_prepend_qemux86 := "${THISDIR}/${BPN}:"
SRC_URI_append_qemux86 = " file://0002-render-into-wl_surface-without-sub-surface.patch"
