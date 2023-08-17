# Copyright (c) 2018-2023 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos4"

# modify tests PACKAGECONFIG to add optional gsl and gmp dependencies
# this cannot be merged to oe-core, recipe, because gsl recipe is in
# meta-oe and there isn't separate configure flag for gsl support
# inside tests gslutils and with newer oe-core with RSS this isn't an
# issue, because gsl, gmp are never autodetected from RSS
# Fixes:
# ERROR: gstreamer1.0-1.14.2-r0 do_package_qa: QA Issue: gstreamer1.0-ptest rdepends on gsl, but it isn't a build dependency, missing gsl in DEPENDS or PACKAGECONFIG? [build-deps]
# ERROR: gstreamer1.0-1.14.2-r0 do_package_qa: QA Issue: gstreamer1.0-ptest rdepends on gmp, but it isn't a build dependency, missing gmp in DEPENDS or PACKAGECONFIG? [build-deps]
PACKAGECONFIG[tests] = "-Dtests=enabled -Dinstalled_tests=true,-Dtests=disabled -Dinstalled_tests=false,gsl gmp"

FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"
SRC_URI:append:qemux86 = " file://0001-Add-support-for-seamless-seek-trickplay.patch"
SRC_URI:append:qemux86-64 = " file://0001-Add-support-for-seamless-seek-trickplay.patch"
