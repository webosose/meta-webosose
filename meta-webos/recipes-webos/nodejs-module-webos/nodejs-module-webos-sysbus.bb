# Copyright (c) 2012-2019 LG Electronics, Inc.

SUMMARY = "A module for nodejs that allows Javascript access to the webOS system bus"
AUTHOR = "Anatolii Sakhnik <anatolii.sakhnik@lge.com>"
SECTION = "webos/nodejs/module"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

DEPENDS = "glib-2.0 luna-service2 node-gyp-native"

WEBOS_VERSION = "3.0.1-2_0f31cdf26ad9a4ea97a35643acb3abcbe0ac16c5"
PR = "r10"

inherit webos_component
inherit webos_public_repo
inherit webos_enhanced_submissions
inherit webos_library
inherit webos_system_bus

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
    # used by binding.gyp
    export webos_servicesdir="${webos_servicesdir}" webos_prefix="${webos_prefix}"
    node-gyp --arch ${TARGET_ARCH} --nodedir "${WORKDIR}/node-v${NODE_VERSION}" configure
}

do_compile() {
    export HOME=${WORKDIR}
    export LD="${CXX}"
    export GYP_DEFINES="sysroot=${STAGING_DIR_HOST}"
    # used by binding.gyp
    export webos_servicesdir="${webos_servicesdir}" webos_prefix="${webos_prefix}"
    node-gyp --arch ${TARGET_ARCH} build
}

do_install() {
    install -d ${D}${libdir}/nodejs
    install ${S}/build/Release/webos-sysbus.node ${D}${libdir}/nodejs/webos-sysbus.node
    install ${S}/src/palmbus.js ${D}${libdir}/nodejs/palmbus.js
}

# XXX Temporarily add symlink to old location until all users of it are changed
FILES_${PN} += "${webos_prefix}/nodejs"
do_install_append() {
    install -d ${D}${webos_prefix}/nodejs
    ln -svnf ${libdir}/nodejs/palmbus.js ${D}${webos_prefix}/nodejs/
    # The CMake build did this with macros
    install -d ${D}${webos_sysbus_rolesdir}
    sed "s|@WEBOS_INSTALL_BINDIR@|$bindir|" < ${S}/files/sysbus/com.webos.nodejs.role.json.in > ${D}${webos_sysbus_rolesdir}/com.webos.nodejs.role.json
}

FILES_${PN} += "${libdir}/nodejs"
