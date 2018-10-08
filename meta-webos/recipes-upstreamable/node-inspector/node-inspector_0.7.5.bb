# Copyright (c) 2013-2018 LG Electronics, Inc.

SUMMARY = "node-inspector in order to support the remote debugging"
AUTHOR = "Hyunsung Lee <hyunsung.lee@lge.com>"
SECTION = "devel/tools"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE;md5=480ea7e44791280d20fa258a33030275"

DEPENDS = "nodejs-native node-gyp-packages-native node-gyp-native"
PR = "r2"

SRC_URI = "git://github.com/${BPN}/${BPN};protocol=https;branch=0.7.x \
           file://node-inspector"

SRCREV = "dcd3125aff325d439871b356fe71d779dc50e5c6"

S = "${WORKDIR}/git"

do_compile() {
    export HOME=${WORKDIR}

    cd ${S}
    # configure cache to be in working directory
    ${STAGING_BINDIR_NATIVE}/npm set cache ${WORKDIR}/npm_cache

    # clear local cache prior to each compile
    ${STAGING_BINDIR_NATIVE}/npm cache clear --force

    ${STAGING_BINDIR_NATIVE}/npm install --production
    cd ${B}
}

do_install() {
    install -d ${D}${bindir}
    cp -R --no-dereference --preserve=mode,links -v ${WORKDIR}/${BPN} ${D}${bindir}/
    install -d ${D}${datadir}/node-inspector
    cp -R --no-dereference --preserve=mode,links -v ${S}/* ${D}${datadir}/${BPN}/
}

VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS_${PN}_append_class-target = " ${VIRTUAL-RUNTIME_bash}"
