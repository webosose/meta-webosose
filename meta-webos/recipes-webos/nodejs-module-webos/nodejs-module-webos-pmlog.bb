# Copyright (c) 2012-2020 LG Electronics, Inc.

require nodejs-module-webos.inc

SUMMARY = "A module for nodejs that allows Javascript access to the webOS logging system"
LIC_FILES_CHKSUM += "file://oss-pkg-info.yaml;md5=d486dd326df35bb9d577c353691f0455"

DEPENDS += "pmloglib vim-native"

WEBOS_VERSION = "3.0.1-5_b5ee1f75bf45bf2628a81933dfc4f286f19e5d62"
PR = "r12"

do_configure() {
    cd src
    sh -c "xxd -i pmloglib.js > pmloglib.js.h"
    cd ..
    node-gyp --arch ${TARGET_ARCH} --nodedir "${WORKDIR}/node-v${NODE_VERSION}" configure
}

do_compile() {
    node-gyp --arch ${TARGET_ARCH} build
}

WEBOS_NODE = "pmloglib.node"
