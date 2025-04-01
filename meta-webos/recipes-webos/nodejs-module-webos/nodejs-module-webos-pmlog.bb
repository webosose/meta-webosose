# Copyright (c) 2012-2025 LG Electronics, Inc.

require nodejs-module-webos.inc

AUTHOR = "Rajesh Gopu I.V <rajeshgopu.iv@lge.com>"
SUMMARY = "A module for nodejs that allows Javascript access to the webOS logging system"
LIC_FILES_CHKSUM += "file://oss-pkg-info.yaml;md5=d486dd326df35bb9d577c353691f0455"

DEPENDS += "pmloglib vim-native"

WEBOS_VERSION = "3.0.1-8_bf9622036e9fcfd0de2fe135ab3039742c73ad05"
PR = "${INC_PR}.0"

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

# FIXME-buildpaths!!!
# [WRP-10883] buildpath QA issues
# http://gecko.lge.com:8000/Errors/Details/898327
# ERROR: QA Issue: File /usr/lib/nodejs/.debug/pmloglib.node in package nodejs-module-webos-pmlog-dbg contains reference to TMPDIR [buildpaths]
ERROR_QA:remove = "buildpaths"
WARN_QA:append = " buildpaths"
