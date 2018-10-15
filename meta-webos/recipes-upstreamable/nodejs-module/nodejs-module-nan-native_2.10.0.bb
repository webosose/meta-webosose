# Copyright (c) 2018 LG Electronics, Inc.

SUMMARY = "A module for nodejs nan"
DESCRIPTION = "A header file filled with macro and utility goodness for making add-on development for Node.js"
HOMEPAGE = "https://github.com/nodejs/nan#readme"
SECTION = "webos/nodejs/module"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=e6270b7774528599f2623a4aeb625829"

DEPENDS = "node-gyp-native"

PR = "r1"
inherit native
SRC_URI = "https://registry.npmjs.org/nan/-/nan-2.10.0.tgz"

SRC_URI[md5sum] = "8578b4fd0af7b14ac6fe933f6d4b0fbb"
SRC_URI[sha256sum] = "600305681aa0bdebcffc5b89529f39bcf2d56f953b8c50f54226d0ca78ba1b84"

S = "${WORKDIR}/package"

do_install() {
    install -d ${D}${libdir}/node_modules
    install -d ${D}${libdir}/node_modules/nan
    install ${S}/*.h ${D}${libdir}/node_modules/nan/
    for SUB in include_dirs.js package.json tools ; do
        cp -R --no-dereference --preserve=mode,links -v $SUB ${D}${libdir}/node_modules/nan
    done
}
