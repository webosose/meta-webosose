# Copyright (c) 2016-2021 LG Electronics, Inc.

DESCRIPTION = "enact-dev command-line tools used by webOS"
AUTHOR = "Jason Robitaille <jason.robitaille@lge.com>"
SECTION = "webos/devel/tools"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

# Dependencies:
#   - nodejs-native to get node & npm
#   - coreutils-native to use timeout utility to prevent frozen NPM processes
DEPENDS_append = " nodejs-native coreutils-native"

inherit webos_enact_repo
inherit native
inherit webos_npm_env

# NOTE: It's only necessary to bump PR if the recipe itself changes
# No need to bump PR when changing the values of PV and SRCREV (below)
PR = "r9"

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

# npm install on cli & jsdoc-to-ts
do_compile() {
    bbnote "Enact cli & jsdoc-to-ts npm install"
    for LOC_TOOL in ${S}/* ; do
        ATTEMPTS=0
        STATUS=-1
        while [ ${STATUS} -ne 0 ] ; do
            ATTEMPTS=$(expr ${ATTEMPTS} + 1)
            if [ ${ATTEMPTS} -gt 5 ] ; then
                bberror "All attempts to NPM install have failed; exiting!"
                exit ${STATUS}
            fi

            bbnote "NPM install attempt #${ATTEMPTS} (of 5)..." && echo
            STATUS=0
            timeout --kill-after=5m 15m ${WEBOS_NPM_BIN} ${WEBOS_NPM_INSTALL_FLAGS} install -C ${LOC_TOOL} || eval "STATUS=\$?"
            if [ ${STATUS} -ne 0 ] ; then
                bbwarn "...NPM process failed with status ${STATUS}"
            else
                bbnote "...NPM process succeeded" && echo
            fi
        done
    done
}

# Install enact-dev in sysroot for use in Enact app recipes
do_install() {
    install -d ${D}${base_prefix}/opt
    cp -R --no-dereference --preserve=mode,links -v ${S}/* ${D}${base_prefix}/opt
}

sysroot_stage_all_append() {
    # files installed to /opt don't get staged by default so we must force /opt to be staged
    sysroot_stage_dir ${D}${base_prefix}/opt ${SYSROOT_DESTDIR}${base_prefix}/opt
}
