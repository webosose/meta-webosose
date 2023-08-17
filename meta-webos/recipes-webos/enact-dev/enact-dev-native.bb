# Copyright (c) 2016-2023 LG Electronics, Inc.

# Maintained by Seungho Park <seunghoh.park@lge.com>
DESCRIPTION = "enact-dev command-line tools used by webOS"
AUTHOR = "EnactUnassigned <enact.swp@lge.com>"
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

PV = "6.0.1"
SRCREV = "6f04791935d250337a081e327f6f90d503fe9c19"
SRCREV_jsdoc-to-ts = "c2d04d1b4a14405a13ee27080fc1545b8c4649cd"

# Skip unneeded tasks
do_configure[noexec] = "1"

# npm install on cli & jsdoc-to-ts
do_compile() {
    bbnote "Enact cli & jsdoc-to-ts npm install"
    for LOC_TOOL in ${S}/* ; do
        ${WEBOS_NPM_BIN} ${WEBOS_NPM_INSTALL_FLAGS} install -C ${LOC_TOOL}
    done
}

# Install enact-dev in sysroot for use in Enact app recipes
do_install() {
    install -d ${D}${base_prefix}/opt
    cp -R --no-dereference --preserve=mode,links -v ${S}/* ${D}${base_prefix}/opt
}

SYSROOT_DIRS += "${base_prefix}/opt"

# Workaround for network access issue during do_compile task
do_compile[network] = "1"
