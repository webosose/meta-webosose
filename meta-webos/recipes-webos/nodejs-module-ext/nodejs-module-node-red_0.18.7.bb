# Copyright (c) 2019 LG Electronics, Inc.

SUMMARY = "Loadable node-red module for nodejs services"
AUTHOR = "Tirthadeep Roy <tirthadeep.roy@lge.com>"
SECTION = "webOS/modules"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=d6f37569f5013072e9490d2194d10ae6"

PR = "r0"

DEPENDS += "nodejs-native"
RDEPENDS_${PN} = "nodejs python"

# If uncomment under 'TODO' on do_install step,
# this package will provide much more things actually. (e.g. express)

SRC_URI = "https://github.com/node-red/node-red/releases/download/${PV}/node-red-${PV}.zip"
SRC_URI[md5sum] = "bdcd269c789f93717c507008bbe69502"
SRC_URI[sha256sum] = "796cf5ce6446a9a77299b88c25f94703a5977ff90b067ed68c50e5c6bb7f321f"

S = "${WORKDIR}/node-red-${PV}"

TARGET_DIR = "${D}${libdir}/node_modules"

def get_nodejs_arch(d):
    target_arch = d.getVar('TRANSLATED_TARGET_ARCH', True)

    if target_arch == "x86-64":
        target_arch = "x64"
    elif target_arch == "aarch64":
        target_arch = "arm64"
    elif target_arch == "powerpc":
        target_arch = "ppc"
    elif target_arch == "powerpc64":
        target_arch = "ppc64"
    elif (target_arch == "i486" or target_arch == "i586" or target_arch == "i686"):
        target_arch = "ia32"

    return target_arch

NPM_CACHE_DIR ?= "${WORKDIR}/npm_cache"
NPM_REGISTRY ?= "http://registry.npmjs.org/"
NPM_ARCH = "${@get_nodejs_arch(d)}"
NPM_INSTALL_FLAGS ?= "--production --without-ssl --insecure --no-optional --verbose"

do_compile() {
    cd ${S}
    # configure cache to be in working directory
    ${STAGING_BINDIR_NATIVE}/npm set cache ${NPM_CACHE_DIR}

    # clear local cache prior to each compile
    ${STAGING_BINDIR_NATIVE}/npm cache clear --force

    ${STAGING_BINDIR_NATIVE}/npm --registry=${NPM_REGISTRY} --arch=${NPM_ARCH} --target_arch=${NPM_ARCH} ${NPM_INSTALL_FLAGS} install
}

do_install() {
    install -d ${TARGET_DIR}/node-red
    cp -R --no-dereference --preserve=mode,links -v ${S}/* ${TARGET_DIR}/node-red

    # TODO Move dependent packages to top node_modules dir
    # mv ${TARGET_DIR}/node-red/node_modules/* ${TARGET_DIR}
}

FILES_${PN} += "${libdir}/node_modules"

# ERROR: QA Issue: /usr/lib/node_modules/node-red/nodes/core/hardware/nrgpio contained in package nodejs-module-node-red requires /bin/bash, but no providers found in RDEPENDS_nodejs-module-node-red? [file-rdeps]
VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS_${PN}_append_class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS_${PN}_remove = "bash"
