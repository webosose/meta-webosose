# Copyright (c) 2013-2022 LG Electronics, Inc.

SUMMARY = "A localization tool is written in JavaScript"
AUTHOR = "Seonmi Jin <seonmi1.jin@lge.com>"
SECTION = "webos/devel/tools"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=86d3f3a95c324c9479bd8986968f4327"

PR = "r8"

inherit webos_npm_env
inherit native
DEPENDS = "nodejs-native node-gyp-packages-native"

SRC_URI = "git://github.com/iLib-js/ilib-loctool-webos-dist.git;branch=master;protocol=https"
S = "${WORKDIR}/git"

# PV is the version of the ilib-loctool-webos-dist distribution, as tagged in the
# iLib-js/ilib-loctool-webos-dist repository on GitHub. This version should correspond to the
# tag whose hash is specified in SRCREV, so PV and SRCREV will always change
# together.
PV = "1.10.0"
SRCREV = "afad56ae71c5b0896e75690695d179d3de731918"

# Skip the unwanted tasks
do_configure[noexec] = "1"

do_compile() {
    ${WEBOS_NPM_BIN} ${WEBOS_NPM_INSTALL_FLAGS} install
}

# Install js-loctool in sysroot for use in localization recipes
do_install() {
    install -d ${D}${base_prefix}/opt/js-loctool
    cp -R --no-dereference --preserve=mode,links -v ${S}/* ${D}${base_prefix}/opt/js-loctool
    # The build directory isn't needed in sysroot and python3 symlink installed with nodejs causes:
    # ERROR: localization-tool-native-1.7.0-r7 do_populate_sysroot: sstate found an absolute path symlink /OE/BUILD/work/x86_64-linux/localization-tool-native/1.7.0-r7/recipe-sysroot-native/opt/js-loctool/node_modules/node-expat/build/node_gyp_bins/python3 pointing at /OE/BUILD/hosttools/python3. Please replace this with a relative link.
    # caused by https://github.com/nodejs/node-gyp/commit/b9ddcd5bbd93b05b03674836b6ebdae2c2e74c8c
    rm -rf ${D}${base_prefix}/opt/js-loctool/node_modules/node-expat/build/node_gyp_bins
}

SYSROOT_DIRS += "${base_prefix}/opt"

# Workaround for network access issue during do_compile task
do_compile[network] = "1"
