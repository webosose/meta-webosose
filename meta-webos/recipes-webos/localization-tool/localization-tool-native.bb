# Copyright (c) 2013-2024 LG Electronics, Inc.

SUMMARY = "A localization tool is written in JavaScript"
AUTHOR = "Seonmi Jin <seonmi1.jin@lge.com>"
SECTION = "webos/devel/tools"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=86d3f3a95c324c9479bd8986968f4327"

PR = "r10"

inherit webos_npm_env
inherit native
DEPENDS = "nodejs-native node-gyp-packages-native"

SRC_URI = "git://github.com/iLib-js/ilib-loctool-webos-dist.git;branch=main;protocol=https"
S = "${WORKDIR}/git"

# PV is the version of the ilib-loctool-webos-dist distribution, as tagged in the
# iLib-js/ilib-loctool-webos-dist repository on GitHub. This version should correspond to the
# tag whose hash is specified in SRCREV, so PV and SRCREV will always change
# together.
PV = "1.15.4"
SRCREV = "77386a915785477e7119a4c196810f8bf86c29a1"

# Skip the unwanted tasks
do_configure[noexec] = "1"

do_compile() {
    ${WEBOS_NPM_BIN} ${WEBOS_NPM_INSTALL_FLAGS} install
}

# Install js-loctool in sysroot for use in localization recipes
do_install() {
    install -d ${D}${base_prefix}/opt/js-loctool
    cp -R --no-dereference --preserve=mode,links -v ${S}/* ${D}${base_prefix}/opt/js-loctool
}

SYSROOT_DIRS += "${base_prefix}/opt"

# Workaround for network access issue during do_compile task
do_compile[network] = "1"
