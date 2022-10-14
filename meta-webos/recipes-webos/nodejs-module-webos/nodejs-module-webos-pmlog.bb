# Copyright (c) 2012-2022 LG Electronics, Inc.

require nodejs-module-webos.inc

AUTHOR = "Seokhee Lee <seokhee.lee@lge.com>"
SUMMARY = "A module for nodejs that allows Javascript access to the webOS logging system"
LIC_FILES_CHKSUM += "file://oss-pkg-info.yaml;md5=d486dd326df35bb9d577c353691f0455"

DEPENDS += "pmloglib vim-native"

WEBOS_VERSION = "3.0.1-7_f08fbbec80e018cccf71d4f1ddae8a3f43a8895b"
PR = "r14"

SRC_URI += "file://0001-Fix-build-for-nodejs-14.patch"

do_configure() {
    cd src
    sh -c "xxd -i pmloglib.js > pmloglib.js.h"
    cd ..
    ${WEBOS_NODE_GYP} configure
}

do_compile() {
    ${WEBOS_NODE_GYP} build
}

WEBOS_NODE = "pmloglib.node"
