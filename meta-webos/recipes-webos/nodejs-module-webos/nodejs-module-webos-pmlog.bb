# Copyright (c) 2012-2020 LG Electronics, Inc.

SUMMARY = "A module for nodejs that allows Javascript access to the webOS logging system"
AUTHOR = "Anatolii Sakhnik <anatolii.sakhnik@lge.com>"
SECTION = "webos/nodejs/module"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10 \
                    file://oss-pkg-info.yaml;md5=abbc2c32ca736a8d67501064544ff10a"

DEPENDS = "pmloglib node-gyp-native vim-native"

WEBOS_VERSION = "3.0.1-3_62c93c2b61f40225b5d88ee1aa331c46f5aa2b3d"
PR = "r8"

inherit webos_component
inherit webos_public_repo
inherit webos_enhanced_submissions
inherit webos_library
inherit webos_filesystem_paths
inherit python3native

export PYTHON = "python"

NODE_VERSION = "12.14.1"

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE} \
    https://nodejs.org/dist/v${NODE_VERSION}/node-v${NODE_VERSION}.tar.xz;name=node \
"
SRC_URI[node.md5sum] = "1c78a75f5c95321f533ecccca695e814"
SRC_URI[node.sha256sum] = "877b4b842318b0e09bc754faf7343f2f097f0fc4f88ab9ae57cf9944e88e7adb"

S = "${WORKDIR}/git"

do_install() {
    install -d ${D}${libdir}/nodejs
    install ${S}/src/pmloglib_mock.js ${D}${libdir}/nodejs/pmloglib.js
}

# XXX Temporarily add symlink to old location until all users of it are changed
FILES_${PN} += "${webos_prefix}/nodejs"
do_install_append() {
    install -d ${D}${webos_prefix}/nodejs
    ln -svnf ${libdir}/nodejs/pmloglib.js ${D}${webos_prefix}/nodejs/
}

FILES_${PN} += "${libdir}/nodejs"
