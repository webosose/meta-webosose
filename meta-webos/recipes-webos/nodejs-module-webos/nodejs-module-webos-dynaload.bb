# Copyright (c) 2012-2020 LG Electronics, Inc.

SUMMARY = "A module for nodejs that allows dynamic loading and execution of Javascript files"
AUTHOR = "Anatolii Sakhnik <anatolii.sakhnik@lge.com>"
SECTION = "webos/nodejs/module"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10 \
                    file://oss-pkg-info.yaml;md5=d980a35955bef62f4f06ee11e344c4d0"

DEPENDS = "boost node-gyp-native"

WEBOS_VERSION = "3.0.2-2_c3299eec0fa197db7fe544183eb089e2425f4245"
PR = "r7"

inherit webos_component
inherit webos_public_repo
inherit webos_enhanced_submissions
inherit webos_library
inherit webos_filesystem_paths
inherit python3native

export PYTHON = "python"

NODE_VERSION = "8.12.0"

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE} \
    https://nodejs.org/dist/v${NODE_VERSION}/node-v${NODE_VERSION}.tar.xz;name=node \
"
SRC_URI[node.md5sum] = "8b3abd033dae96b6fadcb6a872a44d3c"
SRC_URI[node.sha256sum] = "5a9dff58016c18fb4bf902d963b124ff058a550ebcd9840c677757387bce419a"
S = "${WORKDIR}/git"

do_configure() {
    export HOME=${WORKDIR}
    export LD="${CXX}"
    export GYP_DEFINES="sysroot=${STAGING_DIR_HOST}"
    node-gyp --arch ${TARGET_ARCH} --nodedir "${WORKDIR}/node-v${NODE_VERSION}" configure
}

do_compile() {
    export HOME=${WORKDIR}
    export LD="${CXX}"
    export GYP_DEFINES="sysroot=${STAGING_DIR_HOST}"
    node-gyp --arch ${TARGET_ARCH} build
}

do_install() {
    install -d ${D}${libdir}/nodejs
    install ${S}/build/Release/webos.node ${D}${libdir}/nodejs/webos.node
    install -d ${D}${webos_prefix}/nodejs
    ln -svnf ${libdir}/nodejs/webos.node ${D}${webos_prefix}/nodejs/
}

# XXX Temporarily add symlink to old location until all users of it are changed
FILES_${PN} += "${webos_prefix}/nodejs"
do_install_append() {
    install -d ${D}${webos_prefix}/nodejs
    ln -svnf ${libdir}/nodejs/webos.node ${D}${webos_prefix}/nodejs/
}

FILES_${PN} += "${libdir}/nodejs"
