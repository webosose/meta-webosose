# Copyright (c) 2018 LG Electronics, Inc.

SUMMARY = "A module for nodejs usocket"
HOMEPAGE = "https://github.com/jhs67/usocket#readme"
SECTION = "webos/nodejs/module"
LICENSE = "ISC"
LIC_FILES_CHKSUM = "file://LICENSE;md5=5717bc308dbc03edd5e758d11c5bab65"

DEPENDS = "node-gyp-native nodejs-module-nan-native"

PR = "r1"

SRC_URI = "https://registry.npmjs.org/usocket/-/usocket-0.0.4.tgz \
    file://0001-Fix-crash-issue.patch \
    file://0002-Fix-dangling-pointer.patch \
"

SRC_URI[md5sum] = "a7f97a2da2f55999c91eed8bd3c4e881"
SRC_URI[sha256sum] = "7346cd8a6f805a6941736211c1c8b533a545accc4deda0d4beae7c11ce0bdbe8"

S = "${WORKDIR}/package"

do_configure() {
    export LD="${CXX}"
    export NODE_PATH=${STAGING_LIBDIR_NATIVE}/node_modules
    node-gyp --arch ${TARGET_ARCH} configure
}

do_compile() {
    export LD="${CXX}"
    export NODE_PATH=${STAGING_LIBDIR_NATIVE}/node_modules
    node-gyp --arch ${TARGET_ARCH} build
}

do_install() {
    install -d ${D}${libdir}/node_modules
    install -d ${D}${libdir}/node_modules/usocket
    install -d ${D}${libdir}/node_modules/usocket/node_modules
    for SUB in package.json index.js ; do
        cp -R --no-dereference --preserve=mode,links -v $SUB ${D}${libdir}/node_modules/usocket
    done
    cp -R --no-dereference --preserve=mode,links -v build/Release/uwrap.node ${D}${libdir}/node_modules/usocket/node_modules/
}

FILES_${PN} += "${libdir}/node_modules/usocket"
