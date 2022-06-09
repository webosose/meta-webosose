# Copyright (c) 2012-2022 LG Electronics, Inc.

SUMMARY = "A module for nodejs that allows dynamic loading and execution of Javascript files"
AUTHOR = "Anatolii Sakhnik <anatolii.sakhnik@lge.com>"
SECTION = "webos/nodejs/module"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

DEPENDS = "node-gyp-native"

inherit webos_component
inherit webos_public_repo
inherit webos_enhanced_submissions
inherit webos_library
inherit webos_filesystem_paths
inherit python3native

export PYTHON = "python3"

NODE_VERSION = "12.22.2"

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE} \
    https://nodejs.org/dist/v${NODE_VERSION}/node-v${NODE_VERSION}.tar.xz;name=node \
"
SRC_URI[node.sha256sum] = "7fd805571df106f086f4c45e131efed98bfd62628d9dec96bd62f8c11b0c48dc"

S = "${WORKDIR}/git"

do_configure:prepend() {
    export HOME=${WORKDIR}
    export LD="${CXX}"
}

do_compile:prepend() {
    export HOME=${WORKDIR}
    export LD="${CXX}"
}

do_install() {
    install -d ${D}${libdir}/nodejs
    install ${S}/build/Release/${WEBOS_NODE} ${D}${libdir}/nodejs/${WEBOS_NODE}
}

FILES:${PN} += "${libdir}/nodejs"
