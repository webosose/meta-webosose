# Copyright (c) 2012-2023 LG Electronics, Inc.

require nodejs-module-webos.inc

AUTHOR = "Rajesh Gopu I.V <rajeshgopu.iv@lge.com>"
SUMMARY = "A module for nodejs that allows dynamic loading and execution of Javascript files"
LIC_FILES_CHKSUM += "file://oss-pkg-info.yaml;md5=d980a35955bef62f4f06ee11e344c4d0"

DEPENDS += "boost"

WEBOS_VERSION = "3.0.2-3_ff4769966ef245bbe70e36296c1fb1e1a3205d23"
PR = "r11"

SRC_URI += "file://0001-Fix-build-for-nodejs-14.patch"

do_configure() {
    export GYP_DEFINES="sysroot=${STAGING_DIR_HOST}"
    ${WEBOS_NODE_GYP} configure
}

do_compile() {
    export GYP_DEFINES="sysroot=${STAGING_DIR_HOST}"
    ${WEBOS_NODE_GYP} build
}

WEBOS_NODE = "webos.node"
