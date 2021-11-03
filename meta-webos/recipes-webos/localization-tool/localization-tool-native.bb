# Copyright (c) 2013-2021 LG Electronics, Inc.

SUMMARY = "A localization tool is written in JavaScript"
AUTHOR = "Goun Lee<goun.lee@lge.com>"
SECTION = "webos/devel/tools"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=86d3f3a95c324c9479bd8986968f4327"

PR = "r6"

inherit pythonnative
inherit webos_npm_env
inherit native
DEPENDS = "nodejs-native node-gyp-packages-native"

SRC_URI = "git://github.com/iLib-js/ilib-loctool-webos-dist.git;branch=master;protocol=https"
S = "${WORKDIR}/git"

# PV is the version of the ilib-loctool-webos-dist distribution, as tagged in the
# iLib-js/ilib-loctool-webos-dist repository on GitHub. This version should correspond to the
# tag whose hash is specified in SRCREV, so PV and SRCREV will always change
# together.
PV = "1.5.0"
SRCREV = "86b83a1bbfa3f0585300bf57c3472af000e8a1fa"

# Skip the unwanted tasks
do_configure[noexec] = "1"

do_compile() {
    ${WEBOS_NPM_BIN} ${WEBOS_NPM_INSTALL_FLAGS} install
}

# Install js-loctool in sysroot for use in localization recipes
do_install() {
    install -d ${D}${base_prefix}/opt/js-loctool
    cp -R --no-dereference --preserve=mode,links -v ${S}/* ${D}${base_prefix}/opt/js-loctool
    cd ${D}${base_prefix}/opt/js-loctool
}

sysroot_stage_all_append() {
    # files installed to /opt don't get staged by default so we must force /opt to be staged
    sysroot_stage_dir ${D}${base_prefix}/opt ${SYSROOT_DESTDIR}${base_prefix}/opt
}
