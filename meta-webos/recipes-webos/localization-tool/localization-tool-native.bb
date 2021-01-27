# Copyright (c) 2013-2021 LG Electronics, Inc.

SUMMARY = "A localization tool is written in JavaScript"
AUTHOR = "Goun Lee<goun.lee@lge.com>"
SECTION = "webos/devel/tools"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

PR = "r4"

inherit pythonnative
inherit native
DEPENDS = "nodejs-native node-gyp-packages-native"

SRC_URI = "git://github.com/iLib-js/ilib-loctool-webos-dist.git;branch=master"
S = "${WORKDIR}/git"

# PV is the version of the ilib-loctool-webos-dist distribution, as tagged in the
# iLib-js/ilib-loctool-webos-dist repository on GitHub. This version should correspond to the
# tag whose hash is specified in SRCREV, so PV and SRCREV will always change
# together.
PV = "1.2.1"
SRCREV = "29b07b1ec01ab81667eb4b946446693983b1711b"

# Skip the unwanted tasks
do_configure[noexec] = "1"
do_compile[noexec] = "1"

# Install js-loctool in sysroot for use in localization recipes
do_install() {
    install -d ${D}${base_prefix}/opt/js-loctool
    cp -R --no-dereference --preserve=mode,links -v ${S}/* ${D}${base_prefix}/opt/js-loctool
    cd ${D}${base_prefix}/opt/js-loctool
    rm -fr node_modules
    npm install
}

sysroot_stage_all_append() {
    # clear any existing npm cache
    rm -fr ${TMPDIR}/npm_cache
    # files installed to /opt don't get staged by default so we must force /opt to be staged
    sysroot_stage_dir ${D}${base_prefix}/opt ${SYSROOT_DESTDIR}${base_prefix}/opt
}
