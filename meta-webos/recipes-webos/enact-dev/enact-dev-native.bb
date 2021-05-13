# Copyright (c) 2016-2021 LG Electronics, Inc.

DESCRIPTION = "enact-dev command-line tools used by webOS"
AUTHOR = "Jason Robitaille <jason.robitaille@lge.com>"
SECTION = "webos/devel/tools"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

inherit webos_enact_repo
inherit native

# NOTE: It's only necessary to bump PR if the recipe itself changes
# No need to bump PR when changing the values of PV and SRCREV (below)
PR = "r8"

S = "${WORKDIR}/git"

SRC_URI = " \
    ${ENACTJS_GIT_REPO}/cli.git;name=main;nobranch=1;destsuffix=git/cli \
    ${ENACTJS_GIT_REPO}/cli.git;name=cli-legacy;nobranch=1;destsuffix=git/cli-legacy \
    ${ENACTJS_GIT_REPO}/jsdoc-to-ts.git;name=jsdoc-to-ts;nobranch=1;destsuffix=git/jsdoc-to-ts \
"

# we don't include SRCPV in PV, so we have to manually include SRCREVs in do_fetch vardeps
do_fetch[vardeps] += "SRCREV"
do_fetch[vardeps] += "SRCREV_cli-legacy"
do_fetch[vardeps] += "SRCREV_jsdoc-to-ts"
SRCREV_FORMAT = "main_cli-legacy_jsdoc-to-ts"

# PV is the version of the cli distribution, as tagged in the
# enactjs/cli repository on GitHub. This version should correspond to the
# tag whose hash is specified in SRCREV, so PV and SRCREV will always change
# together.

PV = "4.0.2"
SRCREV = "957de13dd358c6d32df84414e40ad64a8b798aef"
SRCREV_cli-legacy = "bf5012e50bdca62ff596b73a55a5b5f93ccf1069"
SRCREV_jsdoc-to-ts = "91e3709da01f4a8e0d57c2ed80d068789acf37eb"

# Skip unneeded tasks
do_configure[noexec] = "1"
do_compile[noexec] = "1"

# Install enact-dev in sysroot for use in Enact app recipes
do_install() {
    install -d ${D}${base_prefix}/opt
    cp -R --no-dereference --preserve=mode,links -v ${S}/* ${D}${base_prefix}/opt
}

sysroot_stage_all_append() {
    # files installed to /opt don't get staged by default so we must force /opt to be staged
    sysroot_stage_dir ${D}${base_prefix}/opt ${SYSROOT_DESTDIR}${base_prefix}/opt
}
