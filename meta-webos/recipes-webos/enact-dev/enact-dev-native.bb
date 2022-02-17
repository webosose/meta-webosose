# Copyright (c) 2016-2022 LG Electronics, Inc.

DESCRIPTION = "enact-dev command-line tools used by webOS"
AUTHOR = "Seungho Park <seunghoh.park@lge.com>"
SECTION = "webos/devel/tools"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://cli/LICENSE;md5=9456eea7fa7e9e4a4fcdf8e430bd36c8"

# Dependencies:
#   - nodejs-native to get node & npm
#   - coreutils-native to use timeout utility to prevent frozen NPM processes
DEPENDS:append = " nodejs-native coreutils-native"

inherit webos_enact_repo
inherit webos_npm_env
inherit native

# NOTE: It's only necessary to bump PR if the recipe itself changes
# No need to bump PR when changing the values of PV and SRCREV (below)
PR = "r12"

S = "${WORKDIR}/git"

SRC_URI = " \
    ${ENACTJS_GIT_REPO}/cli.git;name=main${WEBOS_GIT_PROTOCOL};nobranch=1;destsuffix=git/cli \
    ${ENACTJS_GIT_REPO}/jsdoc-to-ts.git;name=jsdoc-to-ts${WEBOS_GIT_PROTOCOL};nobranch=1;destsuffix=git/jsdoc-to-ts \
"

# we don't include SRCPV in PV, so we have to manually include SRCREVs in do_fetch vardeps
do_fetch[vardeps] += "SRCREV"
do_fetch[vardeps] += "SRCREV_jsdoc-to-ts"
SRCREV_FORMAT = "main_jsdoc-to-ts"

# PV is the version of the cli distribution, as tagged in the
# enactjs/cli repository on GitHub. This version should correspond to the
# tag whose hash is specified in SRCREV, so PV and SRCREV will always change
# together.

PV = "5.0.2"
SRCREV = "37876eebfa39545cabad4c4d1aa30ecfec6417fc"
SRCREV_jsdoc-to-ts = "059b9395e5804f943c3eef1afe7a0f80ef7c42ca"

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

sysroot_stage_all:append() {
    # files installed to /opt don't get staged by default so we must force /opt to be staged
    sysroot_stage_dir ${D}${base_prefix}/opt ${SYSROOT_DESTDIR}${base_prefix}/opt
}

# Workaround for network access issue during do_compile task
do_compile[network] = "1"
