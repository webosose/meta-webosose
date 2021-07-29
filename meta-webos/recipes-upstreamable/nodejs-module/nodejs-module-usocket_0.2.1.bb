# Copyright (c) 2018-2021 LG Electronics, Inc.

SUMMARY = "A module for nodejs usocket"
HOMEPAGE = "https://github.com/jhs67/usocket#readme"
SECTION = "webos/nodejs/module"
LICENSE = "ISC"
LIC_FILES_CHKSUM = "file://LICENSE;md5=5717bc308dbc03edd5e758d11c5bab65"

DEPENDS = "node-gyp-native nodejs-module-nan-native"

PR = "r4"

SRC_URI = "https://registry.npmjs.org/usocket/-/usocket-${PV}.tgz;subdir=${BP}"

SRC_URI[sha256sum] = "f3aa737df3d25695080dabe315a62f9d95526259902197ef0967e4904eae4460"

SRC_URI += "file://0001-Add-debug-in-dependency.patch"

S = "${WORKDIR}/${BP}/package"

do_configure() {
    export LD="${CXX}"
    export NODE_PATH=${STAGING_LIBDIR_NATIVE}/node_modules
    node-gyp --arch ${TARGET_ARCH} configure
}

do_compile() {
    export LD="${CXX}"
    export NODE_PATH=${STAGING_LIBDIR_NATIVE}/node_modules
    npm --arch=${TARGET_ARCH} --production --verbose install
}

do_install() {
    install -d ${D}${libdir}/node_modules
    install -d ${D}${libdir}/node_modules/usocket
    install -d ${D}${libdir}/node_modules/usocket/node_modules
    install -d ${D}${libdir}/node_modules/usocket/build/Release
    for SUB in package.json index.js ; do
        cp -R --no-dereference --preserve=mode,links -v $SUB ${D}${libdir}/node_modules/usocket
    done
    cp -R --no-dereference --preserve=mode,links -v build/Release/uwrap.node ${D}${libdir}/node_modules/usocket/node_modules/
    cp -R --no-dereference --preserve=mode,links -v build/Release/uwrap.node ${D}${libdir}/node_modules/usocket/build/Release/
    cp -r ${S}/node_modules/ ${D}${libdir}/node_modules/usocket
}

FILES:${PN} += "${libdir}/node_modules/usocket"
