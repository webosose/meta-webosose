# Copyright (c) 2018-2020 LG Electronics, Inc.

SUMMARY = "A module for nodejs nan"
DESCRIPTION = "A header file filled with macro and utility goodness for making add-on development for Node.js"
HOMEPAGE = "https://github.com/nodejs/nan#readme"
SECTION = "webos/nodejs/module"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=e6270b7774528599f2623a4aeb625829"

DEPENDS = "node-gyp-native"

PR = "r1"
inherit native
SRC_URI = "https://registry.npmjs.org/nan/-/nan-${PV}.tgz"

SRC_URI[sha256sum] = "5f06c3b322447cf3f1c9d55897cee1c8f1fecb3d42ee04c1fd8490a6d6bcbdc3"

S = "${WORKDIR}/package"

do_install() {
    install -d ${D}${libdir}/node_modules
    install -d ${D}${libdir}/node_modules/nan
    install ${S}/*.h ${D}${libdir}/node_modules/nan/
    for SUB in include_dirs.js package.json tools ; do
        cp -R --no-dereference --preserve=mode,links -v $SUB ${D}${libdir}/node_modules/nan
    done
}
