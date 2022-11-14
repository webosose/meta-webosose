# Copyright (c) 2018-2022 LG Electronics, Inc.

SUMMARY = "A module for nodejs usocket"
HOMEPAGE = "https://github.com/jhs67/usocket#readme"
SECTION = "webos/nodejs/module"
LICENSE = "ISC"
LIC_FILES_CHKSUM = "file://LICENSE;md5=5717bc308dbc03edd5e758d11c5bab65"

DEPENDS = "node-gyp-native nodejs-module-nan-native"

inherit webos_npm_env

PR = "r9"

SRC_URI = "https://registry.npmjs.org/usocket/-/usocket-${PV}.tgz;subdir=${BP} \
    ${WEBOS_NODE_SRC_URI} \
    file://0001-Add-debug-in-dependency.patch \
"
SRC_URI[sha256sum] = "f3aa737df3d25695080dabe315a62f9d95526259902197ef0967e4904eae4460"

S = "${WORKDIR}/${BP}/package"

do_configure() {
    export LD="${CXX}"
    export NODE_PATH=${STAGING_LIBDIR_NATIVE}/node_modules
    ${WEBOS_NODE_GYP} configure
}

do_compile() {
    ${WEBOS_NPM_BIN} ${WEBOS_NPM_INSTALL_FLAGS} install
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

# Workaround for network access issue during do_configure task
# http://gecko.lge.com:8000/Errors/Details/458031
do_configure[network] = "1"
# do_compile needs it as well, but the failure isn't fatal for do_compile
do_compile[network] = "1"

# work around http://gecko.lge.com:8000/Errors/Details/487316
# i686-webos-linux/gcc/i686-webos-linux/11.3.0/ld: error: Release/obj.target/uwrap/src/uwrap.o: relocation R_386_GOTOFF against preemptible symbol _ZTVN3Nan10ObjectWrapE cannot be used when making a shared object
LDFLAGS:append:x86 = "${@bb.utils.contains('DISTRO_FEATURES', 'ld-is-gold', ' -fuse-ld=bfd ', '', d)}"
# ERROR: nodejs-module-usocket-0.2.1-r7 do_package_qa: QA Issue:
# nodejs-module-usocket: ELF binary /usr/lib/node_modules/usocket/node_modules/uwrap.node has relocations in .text
# nodejs-module-usocket: ELF binary /usr/lib/node_modules/usocket/build/Release/uwrap.node has relocations in .text [textrel]
INSANE_SKIP:${PN} += "textrel"
