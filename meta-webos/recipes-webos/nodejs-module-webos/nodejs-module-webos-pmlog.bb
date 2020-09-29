# Copyright (c) 2012-2020 LG Electronics, Inc.

SUMMARY = "A module for nodejs that allows Javascript access to the webOS logging system"
AUTHOR = "Anatolii Sakhnik <anatolii.sakhnik@lge.com>"
SECTION = "webos/nodejs/module"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10 \
                    file://oss-pkg-info.yaml;md5=d486dd326df35bb9d577c353691f0455"

DEPENDS = "pmloglib node-gyp-native vim-native"

WEBOS_VERSION = "3.0.1-5_b5ee1f75bf45bf2628a81933dfc4f286f19e5d62"
PR = "r10"

inherit webos_component
inherit webos_public_repo
inherit webos_enhanced_submissions
inherit webos_library
inherit webos_filesystem_paths
inherit python3native

export PYTHON = "python3"

NODE_VERSION = "12.14.1"

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE} \
    https://nodejs.org/dist/v${NODE_VERSION}/node-v${NODE_VERSION}.tar.xz;name=node \
"
SRC_URI[node.md5sum] = "1c78a75f5c95321f533ecccca695e814"
SRC_URI[node.sha256sum] = "877b4b842318b0e09bc754faf7343f2f097f0fc4f88ab9ae57cf9944e88e7adb"

S = "${WORKDIR}/git"

do_configure() {
    export HOME=${WORKDIR}
    export LD="${CXX}"
    cd src
    sh -c "xxd -i pmloglib.js > pmloglib.js.h"
    cd ..
    node-gyp --arch ${TARGET_ARCH} --nodedir "${WORKDIR}/node-v${NODE_VERSION}" configure
}

do_compile() {
    export HOME=${WORKDIR}
    export LD="${CXX}"
    node-gyp --arch ${TARGET_ARCH} build
}

do_install() {
    install -d ${D}${libdir}/nodejs
    install ${S}/build/Release/pmloglib.node ${D}${libdir}/nodejs/pmloglib.node
}

# XXX Temporarily add symlink to old location until all users of it are changed
FILES_${PN} += "${webos_prefix}/nodejs"
do_install_append() {
    install -d ${D}${webos_prefix}/nodejs
    ln -svnf ${libdir}/nodejs/pmloglib.node ${D}${webos_prefix}/nodejs/
}

FILES_${PN} += "${libdir}/nodejs"
