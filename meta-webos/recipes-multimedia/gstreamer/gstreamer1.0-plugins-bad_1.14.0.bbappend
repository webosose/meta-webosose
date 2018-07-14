# Copyright (c) 2018 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos1"

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

# Needed only for old Yocto 2.2
FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"
SRC_URI += "file://0001-Prepend-PKG_CONFIG_SYSROOT_DIR-to-pkg-config-output.patch"
EXTRA_OECONF_append = " \
    "WAYLAND_PROTOCOLS_SYSROOT_DIR=${STAGING_DIR_TARGET}" \
"
