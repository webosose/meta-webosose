# Copyright (c) 2012-2024 LG Electronics, Inc.

SUMMARY = "A module for nodejs that allows dynamic loading and execution of Javascript files"
AUTHOR = "Anatolii Sakhnik <anatolii.sakhnik@lge.com>"
SECTION = "webos/nodejs/module"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

DEPENDS = "node-gyp-native"

INC_PR = "r19"

inherit webos_component
inherit webos_public_repo
inherit webos_enhanced_submissions
inherit webos_library
inherit webos_filesystem_paths
inherit webos_npm_env
inherit python3native

export PYTHON = "python3"

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE} \
    ${WEBOS_NODE_SRC_URI} \
"

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
    install -d ${D}${nonarch_libdir}/nodejs
    install ${S}/build/Release/${WEBOS_NODE} ${D}${nonarch_libdir}/nodejs/${WEBOS_NODE}
}

FILES:${PN} += "${nonarch_libdir}/nodejs"
