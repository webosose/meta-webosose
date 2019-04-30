# Copyright (c) 2016-2019 LG Electronics, Inc.

DESCRIPTION = "enact-dev command-line tools used by webOS"
AUTHOR = "Jason Robitaille <jason.robitaille@lge.com>"
SECTION = "webos/devel/tools"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

inherit webos_enact_repo
inherit native

# NOTE: It's only necessary to bump PR if the recipe itself changes
# No need to bump PR when changing the values of PV and SRCREV (below)
PR = "r6"

S = "${WORKDIR}/git"

SRC_URI = "${ENACTJS_GIT_REPO}/enact-dev-dist;branch=webpack-3;"


# we don't include SRCPV in PV, so we have to manually include SRCREVs in do_fetch vardeps
do_fetch[vardeps] += "SRCREV"

# PV is the version of the enact-dev distribution, as tagged in the
# enyojs/enact-dev-dist repository on GitHub. This version should correspond to the
# tag whose hash is specified in SRCREV, so PV and SRCREV will always change
# together.

PV = "1.0.14"
SRCREV = "28ce2e1067de550864352a66d9d5ee8638a094f0"

# Skip unneeded tasks
do_configure[noexec] = "1"
do_compile[noexec] = "1"

# Install enact-dev in sysroot for use in Enact app recipes
do_install() {
    install -d ${D}${base_prefix}/opt/enact-dev
    cp -R --no-dereference --preserve=mode,links -v ${S}/* ${D}${base_prefix}/opt/enact-dev
}

sysroot_stage_all_append() {
    # clear any existing npm cache
    rm -fr ${TMPDIR}/npm_cache
    # files installed to /opt don't get staged by default so we must force /opt to be staged
    sysroot_stage_dir ${D}${base_prefix}/opt ${SYSROOT_DESTDIR}${base_prefix}/opt
}
