# Copyright (c) 2018 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos1"

# modify tests PACKAGECONFIG to add optional gsl and gmp dependencies
# this cannot be merged to oe-core, recipe, because gsl recipe is in
# meta-oe and there isn't separate configure flag for gsl support
# inside tests gslutils and with newer oe-core with RSS this isn't an
# issue, because gsl, gmp are never autodetected from RSS
# Fixes:
# ERROR: gstreamer1.0-1.14.2-r0 do_package_qa: QA Issue: gstreamer1.0-ptest rdepends on gsl, but it isn't a build dependency, missing gsl in DEPENDS or PACKAGECONFIG? [build-deps]
# ERROR: gstreamer1.0-1.14.2-r0 do_package_qa: QA Issue: gstreamer1.0-ptest rdepends on gmp, but it isn't a build dependency, missing gmp in DEPENDS or PACKAGECONFIG? [build-deps]
PACKAGECONFIG[tests] = "--enable-tests,--disable-tests,gsl gmp"
